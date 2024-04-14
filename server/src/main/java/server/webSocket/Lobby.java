package server.webSocket;

import org.eclipse.jetty.websocket.api.Session;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;

public class Lobby {
    public int gameID;
    public ArrayList<String> visitors;

    public Lobby(int gameID, ArrayList<String> visitors) {
        this.gameID = gameID;
        this.visitors = visitors;
    }
}
