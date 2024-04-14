package webSocketMessages.serverMessages;

public class Error extends ServerMessage {
    public String getErrorMessage() {
        return errorMessage;
    }

    String errorMessage;
    public Error(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }
}
