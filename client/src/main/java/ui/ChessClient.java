package ui;

import dataAccess.DataAccessException;
import model.AuthData;
import serverFacade.ServerFacade;

import java.util.Arrays;

import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;
import static ui.EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY;


public class ChessClient {
    private String visitorName = null;
    private AuthData authData = null;

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
                case "3", "create game" -> createGame();
                case "4", "list games" -> listGames();
                case "5", "join game" -> joinGame();
                case "6", "join observer" -> joinObserver();
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

    public String createGame() {
        return "created game";
    }

    public String listGames() {
        return "listed games";
    }

    public String joinGame() {
        return "joined game";
    }

    public String joinObserver() {
        return "joined game as observer";
    }
}
