package webSocketMessages.serverMessages;

public class Error extends ServerMessage {
    String error;
    public Error(ServerMessageType type, String error) {
        super(type);
        this.error = error;
    }
}
