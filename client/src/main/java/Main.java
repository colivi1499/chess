import chess.*;
import server.Server;
import ui.Repl;

public class Main {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        Server server = new Server();
        var port = server.run(0);
        System.out.println("Started HTTP server on " + port);
        if (args.length == 1) {
            serverUrl = args[0];
        }
        new Repl(port).run();
    }
}