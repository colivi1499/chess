package ui;

import serverFacade.ServerFacade;

import java.util.Arrays;


public class ChessClient {

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
            return "1. Help\n2. Quit\n3. Login\n4. Register\n";
        else
            return "1. Help\n2. Logout\n3. Create Game\n4. List Games\n5. Join Game\n6. Join Observer";
    }

    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        if (state == State.SIGNEDOUT) {
            return switch (cmd) {
                case "1", "help" -> help();
                case "2", "quit" -> "quit";
                case "3", "login" -> login();
                case "4", "register" -> register();
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

    public String login() {
        return "";
    }

    public String register() {
        return "";
    }

    public String logout() {
        return "";
    }

    public String createGame() {
        return "";
    }

    public String listGames() {
        return "";
    }

    public String joinGame() {
        return "";
    }

    public String joinObserver() {
        return "";
    }
}
