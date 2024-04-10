package server.webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
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
            case JOIN_PLAYER -> enter(userGameCommand.getAuthString(), session);
            case JOIN_OBSERVER -> exit(userGameCommand.getAuthString());
        }
    }

    private void enter(String visitorName, Session session) throws IOException {
        connections.add(visitorName, session);
        var message = String.format("%s is in the shop", visitorName);
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.ARRIVAL, message);
        connections.broadcast(visitorName, serverMessage);
    }

    private void exit(String visitorName) throws IOException {
        connections.remove(visitorName);
        var message = String.format("%s left the shop", visitorName);
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.DEPARTURE, message);
        connections.broadcast(visitorName, serverMessage);
    }

    public void makeNoise(String petName, String sound) throws Exception {
        try {
            var message = String.format("%s says %s", petName, sound);
            var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOISE, message);
            connections.broadcast("", serverMessage);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}