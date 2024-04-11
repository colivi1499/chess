package webSocketFacade;

import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;
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
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
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
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(joinPlayer));
        } catch (IOException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void leavePetShop(String visitorName) throws Exception {
        try {
            var userGameCommand = new UserGameCommand(visitorName);
            this.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
            this.session.close();
        } catch (IOException ex) {
            throw new Exception(ex.getMessage());
        }
    }

}

