package server;

import dataAccess.DataAccessException;
import handlers.*;
import server.webSocket.WebSocketHandler;
import spark.*;

public class Server {


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        try {
            Spark.webSocket("/connect", new WebSocketHandler());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        Spark.delete("/db", new ClearHandler());
        Spark.post("/user", new RegisterHandler());
        Spark.post("/session", new LoginHandler());
        Spark.delete("/session", new LogoutHandler());
        Spark.get("/game", new ListGamesHandler());
        Spark.post("/game", new CreateGameHandler());
        Spark.put("/game", new JoinGameHandler());

        Spark.awaitInitialization();
        return Spark.port();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run(8080);
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
