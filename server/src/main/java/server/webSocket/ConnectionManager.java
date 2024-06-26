package server.webSocket;

import dataAccess.DataAccessException;
import dataAccess.SqlUserDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import service.UserService;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public ConnectionManager() throws DataAccessException {
    }

    public void add(String visitorName, Session session, int gameID) {
        var connection = new Connection(visitorName, session, gameID);
        connections.put(visitorName, connection);
    }

    public void remove(String visitorName) {
        connections.remove(visitorName);
    }


    public void broadcast(String excludeVisitorName, ServerMessage serverMessage,  int gameID) throws IOException, DataAccessException {
        var removeList = new ArrayList<Connection>();

        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.visitorName.equals(excludeVisitorName)) {
                    if (c.gameID == gameID)
                        c.send(serverMessage.toString());
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            remove(c.visitorName);
        }
    }
    public void sendToRootClient(String visitorName, ServerMessage serverMessage) throws IOException {
        if (connections.get(visitorName) != null)
            connections.get(visitorName).send(serverMessage.toString());
    }
}
