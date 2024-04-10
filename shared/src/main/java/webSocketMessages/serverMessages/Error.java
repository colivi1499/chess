package webSocketMessages.serverMessages;

public class Error extends ServerMessage {
    public Error(ServerMessageType type, String error) {
        super(type);
        this.notification = error;
    }
}
