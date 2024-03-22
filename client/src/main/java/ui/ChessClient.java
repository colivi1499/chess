package ui;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthData;
import result.CreateGameResult;
import result.GameResult;
import result.ListGamesResult;
import serverFacade.ServerFacade;

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
    private final ServerFacade server;
    private final Repl repl;
    private State state = State.SIGNEDOUT;

    public ChessClient(int port, Repl repl) {
        this.port = port;
        this.repl = repl;
        this.server = new ServerFacade(port);
    }
    public String help() {
        if (state == State.SIGNEDOUT)
            return String.format("1. Help %s- with possible commands%s\n2. Quit %s- playing chess%s\n3. Login <username> <password> %s- to play chess%s\n4. Register <username> <password> <email> %s- to create an account%s\n",
                    SET_TEXT_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLUE, SET_TEXT_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLUE, SET_TEXT_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLUE, SET_TEXT_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLUE);
        else
            return "1. Help\n2. Logout\n3. Create Game\n4. List Games\n5. Join Game\n6. Join Observer";
    }

    public String eval(String input) throws ArgumentException, DataAccessException {
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
        } else {
            return switch (cmd) {
                case "1", "help" -> help();
                case "2", "logout" -> logout();
                case "3", "create" -> createGame(params);
                case "4", "list" -> listGames();
                case "5", "join" -> joinGame(params);
                case "6", "joinobserver" -> joinObserver(params);
                default -> help();
            };
        }
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

    public String register(String... params) throws DataAccessException, ArgumentException {
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

    public String joinGame(String... params) throws ArgumentException, DataAccessException {
        if (params.length == 1) {
            int gameNumber = Integer.parseInt(params[0]);
            if (gameNumber < 1 || gameNumber > games.size()) {
                throw new ArgumentException("Invalid game number");
            }
            int gameId = games.get(gameNumber - 1).gameID();
            server.joinGame(null, gameId, authData.authToken());
            return String.format("You joined game %d.", gameId);
        } else if (params.length == 2) {
            int gameNumber = Integer.parseInt(params[0]);
            if (gameNumber < 1 || gameNumber > games.size()) {
                throw new ArgumentException("Invalid game number");
            }
            int gameId = games.get(gameNumber - 1).gameID();
            if (params[1].equals("white"))
                server.joinGame("WHITE", gameId, authData.authToken());
            else if (params[1].equals("black"))
                server.joinGame("BLACK", gameId, authData.authToken());
            else throw new ArgumentException("Please enter WHITE or BLACK");
            return String.format("You joined game %d", gameId);
        }
        throw new ArgumentException("Join game with: 5 <game ID> <WHITE|BLACK|<empty>>");
    }

    public String joinObserver(String... params) throws DataAccessException, ArgumentException {
        if (params.length == 1) {
            int gameNumber = Integer.parseInt(params[0]);
            if (gameNumber < 1 || gameNumber > games.size()) {
                throw new ArgumentException("Invalid game number");
            }
            int gameId = games.get(gameNumber - 1).gameID();
            server.joinGame(null, gameId, authData.authToken());
            return String.format("You joined game %d as an observer.", gameId);
        } else
            throw new ArgumentException("Join as an observer with: 6 <game ID>");
    }
}
