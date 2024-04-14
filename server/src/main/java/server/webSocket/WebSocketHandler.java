package server.webSocket;

import chess.ChessGame;
import chess.ChessPosition;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
import dataAccess.SqlGameDAO;
import dataAccess.SqlUserDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import service.UserService;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Timer;


@WebSocket
public class WebSocketHandler {
    private final UserService userService = new UserService(new SqlUserDAO());

    private final server.webSocket.ConnectionManager connections = new ConnectionManager();

    public WebSocketHandler() throws DataAccessException {
    }


    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapter(UserGameCommand.class, new UserGameCommand.UserGameCommandDeserializer()).create();
        UserGameCommand userGameCommand = gson.fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer((JoinPlayer) userGameCommand, session);
            case JOIN_OBSERVER -> joinObserver((JoinObserver) userGameCommand, session);
            case MAKE_MOVE -> makeMove((MakeMove) userGameCommand, session);
            case RESIGN -> resign((Resign) userGameCommand, session);
            case LEAVE -> leave((Leave) userGameCommand);
        }
    }

    private void joinPlayer(JoinPlayer joinPlayer, Session session) throws IOException {
        connections.add(joinPlayer.getAuthString(), session);
        connections.addLobby(joinPlayer.getGameID(), joinPlayer.getAuthString());
        var message = String.format("%s joined the game as %s.", joinPlayer.getAuthString(), joinPlayer.getTeamColorString());
        var notification = new Notification(message);
        try {
            GameData game = userService.gameService.getGame(joinPlayer.getGameID());
            ChessGame.TeamColor color = joinPlayer.getTeamColor();
            if (color.equals(ChessGame.TeamColor.WHITE) && game.whiteUsername() == null)
                throw new DataAccessException("bad join");
            if (color.equals(ChessGame.TeamColor.BLACK) && game.blackUsername() == null)
                throw new DataAccessException("bad join");
            var loadGameMessage = new LoadGame(game.game());
            userService.joinGame(joinPlayer.getTeamColor(),joinPlayer.getGameID(),joinPlayer.getAuthString());
            connections.sendToRootClient(joinPlayer.getAuthString(), loadGameMessage);
            connections.broadcast(joinPlayer.getAuthString(), notification, joinPlayer.getGameID());
        } catch (DataAccessException e) {
            var errorMessage = new Error("Error: spot already taken");
            if (e.getMessage().equals("bad join"))
                errorMessage = new Error("Error: bad join");
            connections.sendToRootClient(joinPlayer.getAuthString(), errorMessage);
        }
    }

    private void joinObserver(JoinObserver joinObserver, Session session) throws IOException {
        connections.add(joinObserver.getAuthString(), session);
        connections.addLobby(joinObserver.getGameID(), joinObserver.getAuthString());
        var message = String.format("%s joined the game as an observer", joinObserver.getAuthString());
        var loadGameMessage = new LoadGame(new ChessGame());
        var notification = new Notification(message);
        try {
            userService.joinGame(null,joinObserver.getGameID(),joinObserver.getAuthString());
            connections.sendToRootClient(joinObserver.getAuthString(), loadGameMessage);
            connections.broadcast(joinObserver.getAuthString(), notification, joinObserver.getGameID());
        } catch (DataAccessException e) {
            var errorMessage = new Error("Error: spot already taken");
            connections.sendToRootClient(joinObserver.getAuthString(), errorMessage);
        }
    }

    private void makeMove(MakeMove makeMove, Session session) throws IOException {
        try {
            GameData game = userService.gameService.getGame(makeMove.getGameID());
            String username = userService.getUsername(makeMove.getAuthString());
            boolean white = username.equals(game.whiteUsername());
            boolean black = username.equals(game.blackUsername());
            ChessPosition start = makeMove.getMove().getStartPosition();
            ChessPosition end = makeMove.getMove().getEndPosition();
            ChessGame.TeamColor color = game.game().getBoard().getPiece(start).getTeamColor();
            if (game.isOver())
                throw new InvalidMoveException("Game is over");
            if ((white && color == ChessGame.TeamColor.WHITE) || (black && color == ChessGame.TeamColor.BLACK)) {
                game.game().makeMove(makeMove.getMove());
                userService.gameService.updateGame(makeMove.getGameID(), game);
            } else {
                throw new InvalidMoveException("Wrong team");
            }
            var message = String.format("%s moved %s to %s", makeMove.getAuthString(), start.toString(), end.toString() );
            var loadGameMessage = new LoadGame(game.game());
            var notification = new Notification(message);
            connections.broadcast("", loadGameMessage, makeMove.getGameID());
            connections.broadcast(makeMove.getAuthString(), notification, makeMove.getGameID());
        } catch (InvalidMoveException | DataAccessException e) {
            var errorMessage = new Error("Error: " + e.getMessage());
            connections.sendToRootClient(makeMove.getAuthString(), errorMessage);
        }
    }

    private void resign(Resign resign, Session session) throws IOException {
        var message = String.format("%s resigned", resign.getAuthString());
        var notification = new Notification(message);
        try {
            GameData game = userService.gameService.getGame(resign.getGameID());
            String username = userService.getUsername(resign.getAuthString());
            if (!(username.equals(game.whiteUsername()) || username.equals(game.blackUsername()))) {
                throw new InvalidMoveException("You are an observer");
            }
            if (game.isOver())
                throw new InvalidMoveException("The game is already over");

            game.setGameEnded(true);
            userService.gameService.updateGame(resign.getGameID(),game);
            connections.broadcast("", notification, resign.getGameID());
        } catch (DataAccessException | InvalidMoveException e) {
            var errorMessage = new Error("Error: " + e.getMessage());
            connections.sendToRootClient(resign.getAuthString(), errorMessage);
        }
    }

    private void leave(Leave leave) throws IOException {
        try {
            String username = userService.getUsername(leave.getAuthString());
            var message = String.format("%s left the game", username);
            GameData game = userService.gameService.getGame(leave.getGameID());
            var notification = new Notification(message);
            boolean white = username.equals(game.whiteUsername());
            boolean black = username.equals(game.blackUsername());
            if (white)
                game.setWhiteUsername(null);
            if (black)
                game.setBlackUsername(null);
            userService.gameService.updateGame(leave.getGameID(), game);
            connections.broadcast("", notification, leave.getGameID());
            connections.remove(leave.getAuthString());
            connections.removeUserFromLobby(leave.getAuthString(),leave.getGameID());
        } catch (Exception e) {
            var errorMessage = new Error("Error: " + e.getMessage());
            connections.sendToRootClient(leave.getAuthString(), errorMessage);
        }
    }
}