package server.webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Timer;


@WebSocket
public class WebSocketHandler {

    private final server.webSocket.ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(userGameCommand, session);
            case JOIN_OBSERVER -> joinObserver(userGameCommand.getAuthString(), session);
        }
    }

    private void joinPlayer(UserGameCommand joinPlayer, Session session) throws IOException {
        connections.add(joinPlayer.getAuthString(), session);
        var message = String.format("%s joined the game", joinPlayer.getAuthString());
        var loadGameMessage = new LoadGame(new ChessGame());
        var notification = new Notification(message);
        connections.sendMessage(joinPlayer.getAuthString(), loadGameMessage);
        connections.broadcast(joinPlayer.getAuthString(), notification);
    }

    private void joinObserver(String authString, Session session) throws IOException {
        connections.add(authString, session);
        var message = String.format("%s joined the game as an observer", authString);
        var loadGameMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        var notification = new Notification(message);
        connections.broadcast(authString, loadGameMessage);
        connections.broadcast(authString, notification);
    }

}