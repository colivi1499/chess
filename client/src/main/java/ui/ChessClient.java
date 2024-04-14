package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthData;
import result.CreateGameResult;
import result.GameResult;
import result.ListGamesResult;
import serverFacade.ServerFacade;
import webSocketFacade.WebSocketFacade;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.userCommands.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;
import static ui.EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY;


public class ChessClient {
    private String visitorName = null;
    private AuthData authData = null;
    private static ArrayList<GameResult> games = new ArrayList<>();


    private final int port;
    private final String url;
    private final ServerFacade server;
    private final Repl repl;
    private WebSocketFacade ws;
    private State state = State.SIGNEDOUT;
    private int currentGameID = 0;
    private ChessGame.TeamColor currentColor = null;

    public ChessClient(int port, Repl repl) {
        this.port = port;
        this.repl = repl;
        this.server = new ServerFacade(port);
        url = "http://localhost:" + port;
        try {
            ws = new WebSocketFacade(url,repl,server);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String help() {
        if (state == State.SIGNEDOUT)
            return String.format("1. Help %s- with possible commands%s\n2. Quit %s- playing chess%s\n3. Login <username> <password> %s- to play chess%s\n" +
                            "4. Register <username> <password> <email> %s- to create an account%s\n",
                    SET_TEXT_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLUE, SET_TEXT_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLUE, SET_TEXT_COLOR_LIGHT_GREY,
                    SET_TEXT_COLOR_BLUE, SET_TEXT_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLUE);
        else
            return String.format("1. Help %s- with possible commands%s\n2. Logout user\n3. Create Game <name>\n" +
                            "4. List Games %s- in the database%s\n5. Join Game <index> <WHITE|BLACK>\n6. Join Observer",
                    SET_TEXT_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLUE, SET_TEXT_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLUE);
    }

    public String eval(String input) throws Exception {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        if (state == State.SIGNEDOUT) {
            return switch (cmd) {
                case "1", "help" -> help();
                case "2", "quit" -> "quit";
                case "3", "login" -> login(params);
                case "4", "register" -> register(params);
                default -> help();
            };
        } else if (state == State.SIGNEDIN) {
            return switch (cmd) {
                case "1", "help" -> help();
                case "2", "logout" -> logout();
                case "3", "create" -> createGame(params);
                case "4", "list" -> listGames();
                case "5", "join" -> joinGame(params);
                case "6", "joinobserver" -> joinObserver(params);
                default -> help();
            };
        } else if (state == State.INGAME) {
            return switch (cmd) {
                case "m", "move" -> makeMove(params);
                case "d", "redraw" -> redraw();
                case "l", "leave" -> leave();
                case  "r", "resign" -> resign();
                case "h", "highlight" -> highlight(params);
                default -> help();
            };
        }
        return "";
    }

    public String login(String... params) throws DataAccessException, ArgumentException {
        if (params.length == 2) {
            visitorName = params[0];
            authData = server.login(visitorName, params[1]);
            state = State.SIGNEDIN;
            return String.format("You signed in as %s.", visitorName);
        }
        throw new ArgumentException("Sign in with: 3 <username> <password>");
    }

    public String register(String... params) throws Exception {
        if (params.length == 3) {
            visitorName = params[0];
            authData = server.register(visitorName, params[1], params[2]);
            state = State.SIGNEDIN;
            return String.format("You registered as %s.", visitorName);
        }
        throw new ArgumentException("Register with: 4 <username> <password> <email>");
    }

    public String logout() throws DataAccessException {
        server.logout(authData.authToken());
        state = State.SIGNEDOUT;
        return String.format("Logged out %s.", visitorName);
    }

    public String createGame(String... params) throws ArgumentException, DataAccessException {
        if (params.length == 1) {
            CreateGameResult game = server.createGame(params[0], authData.authToken());
            return String.format("You created the game %s.", new Gson().toJson(game));
        }
        throw new ArgumentException("Create game with: 3 <game name>");
    }

    public String listGames() throws DataAccessException {
        ListGamesResult listGamesResult = server.listGames(authData.authToken());
        if (listGamesResult.games().isEmpty())
            return "There are no games in the database";
        String list = "";
        int i = 1;
        games.clear();
        for (GameResult gameResult : listGamesResult.games()) {
            games.add(gameResult);
            list += String.format("%d. %s\n", i, gameResult);
            i++;
        }
        return list;
    }

    public String joinGame(String... params) throws Exception {
        if (params.length == 1) {
            int gameNumber;
            try {
                gameNumber = Integer.parseInt(params[0]);
            } catch (Exception e) {
                throw new ArgumentException("Join game with: 5 <game ID> <WHITE|BLACK|<empty>>");
            }
            if (gameNumber < 1 || gameNumber > games.size()) {
                throw new ArgumentException("Invalid game number");
            }
            int gameId = games.get(gameNumber - 1).gameID();
            ws.joinObserver(new JoinObserver(authData.authToken(), gameId));
            currentGameID = gameId;
            currentColor = null;
            state = State.INGAME;
            return String.format("You joined game %d.", gameId);
        } else if (params.length == 2) {
            int gameNumber = Integer.parseInt(params[0]);
            if (gameNumber < 1 || gameNumber > games.size()) {
                throw new ArgumentException("Invalid game number");
            }
            int gameId = games.get(gameNumber - 1).gameID();
            if (params[1].equals("white")) {
                ws.joinPlayer(new JoinPlayer(authData.authToken(),gameId, ChessGame.TeamColor.WHITE));
                currentGameID = gameId;
                currentColor = ChessGame.TeamColor.WHITE;
                state = State.INGAME;
            }
            else if (params[1].equals("black")) {
                ws.joinPlayer(new JoinPlayer(authData.authToken(),gameId, ChessGame.TeamColor.BLACK));
                currentGameID = gameId;
                currentColor = ChessGame.TeamColor.BLACK;
                state = State.INGAME;
            }

            else throw new ArgumentException("Please enter WHITE or BLACK");
            return String.format("You joined game %d", gameId);
        }
        throw new ArgumentException("Join game with: 5 <game ID> <WHITE|BLACK|<empty>>");
    }

    public String joinObserver(String... params) throws Exception {
        if (params.length == 1) {
            int gameNumber = Integer.parseInt(params[0]);
            if (gameNumber < 1 || gameNumber > games.size()) {
                throw new ArgumentException("Invalid game number");
            }
            int gameId = games.get(gameNumber - 1).gameID();
            ws.joinObserver(new JoinObserver(authData.authToken(), gameId));
            currentGameID = gameId;
            currentColor = null;
            state = State.INGAME;
            return String.format("You joined game %d as an observer.", gameId);
        } else
            throw new ArgumentException("Join as an observer with: 6 <game ID>");
    }

    public String makeMove(String... params) throws Exception {
        if (params.length == 2) {
            PositionConverter converter = new PositionConverter();
            ChessPosition start = converter.convertPosition(params[0]);
            ChessPosition end = converter.convertPosition(params[1]);
            ws.makeMove(new MakeMove(authData.authToken(), currentGameID, new ChessMove(start, end)));
            return "";
        }
        throw new ArgumentException("Must specify start and end position");
    }

    public String leave() throws Exception {
        ws.leave(new Leave(authData.authToken(), currentGameID));
        String s = String.format("You have left game %s", currentGameID);
        currentGameID = 0;
        currentColor = null;
        return s;
    }

    public String resign() throws Exception {
        ws.resign(new Resign(authData.authToken(), currentGameID));
        return "You resigned";
    }

    public String redraw() throws Exception {
        ws.redrawBoard(new RedrawBoard(authData.authToken(), currentGameID, currentColor));
        return "";
    }
    
    public String highlight(String... params) throws Exception {
        if (params.length != 1) {
            throw new ArgumentException("Highlight a piece's moves with: highlight <position>");
        }
        PositionConverter positionConverter = new PositionConverter();
        ChessPosition position = positionConverter.convertPosition(params[0]);
        ws.highlight(new Highlight(authData.authToken(), currentGameID, position, currentColor));

        return "";
    }
}
