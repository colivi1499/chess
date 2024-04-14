package webSocketMessages.serverMessages;

import com.google.gson.Gson;

public class Notification extends ServerMessage {
    public String getMessage() {
        return message;
    }

    String message;
    public Notification(String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }
}
