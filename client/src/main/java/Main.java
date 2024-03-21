import chess.*;
import ui.Repl;

public class Main {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }
        int port = Integer.parseInt(serverUrl.substring(serverUrl.length()-4));
        new Repl(port).run();
    }
}