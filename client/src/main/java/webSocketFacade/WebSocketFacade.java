package webSocketFacade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import serverFacade.ServerFacade;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    javax.websocket.Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws Exception {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    Gson gson = new GsonBuilder().registerTypeAdapter(ServerMessage.class, new ServerMessage.ServerMessageDeserializer()).create();
                    ServerMessage serverMessage = gson.fromJson(message, ServerMessage.class);
                    notificationHandler.notify(serverMessage);
                }
            });
        } catch (IOException | URISyntaxException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(javax.websocket.Session session, EndpointConfig endpointConfig) {
    }

    public void joinPlayer(JoinPlayer joinPlayer) throws Exception {
        Gson gson = new GsonBuilder().registerTypeAdapter(UserGameCommand.class, new UserGameCommand.UserGameCommandDeserializer()).create();
        try {
            this.session.getBasicRemote().sendText(gson.toJson(joinPlayer));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void joinObserver(JoinObserver joinObserver) throws Exception {
        Gson gson = new GsonBuilder().registerTypeAdapter(UserGameCommand.class, new UserGameCommand.UserGameCommandDeserializer()).create();
        try {
            this.session.getBasicRemote().sendText(gson.toJson(joinObserver));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void makeMove(MakeMove makeMove) throws Exception {
        Gson gson = new GsonBuilder().registerTypeAdapter(UserGameCommand.class, new UserGameCommand.UserGameCommandDeserializer()).create();
        try {
            this.session.getBasicRemote().sendText(gson.toJson(makeMove));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void leave(Leave leave) throws Exception {
        Gson gson = new GsonBuilder().registerTypeAdapter(UserGameCommand.class, new UserGameCommand.UserGameCommandDeserializer()).create();
        try {
            this.session.getBasicRemote().sendText(gson.toJson(leave));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void resign(Resign resign) throws Exception {
        Gson gson = new GsonBuilder().registerTypeAdapter(UserGameCommand.class, new UserGameCommand.UserGameCommandDeserializer()).create();
        try {
            this.session.getBasicRemote().sendText(gson.toJson(resign));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void redrawBoard(RedrawBoard redrawBoard) throws Exception {
        Gson gson = new GsonBuilder().registerTypeAdapter(UserGameCommand.class, new UserGameCommand.UserGameCommandDeserializer()).create();
        try {
            this.session.getBasicRemote().sendText(gson.toJson(redrawBoard));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void highlight(Highlight highlight) throws Exception {
        Gson gson = new GsonBuilder().registerTypeAdapter(UserGameCommand.class, new UserGameCommand.UserGameCommandDeserializer()).create();
        try {
            this.session.getBasicRemote().sendText(gson.toJson(highlight));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}

