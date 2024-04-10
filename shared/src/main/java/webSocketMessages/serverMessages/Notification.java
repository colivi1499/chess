package webSocketMessages.serverMessages;

public class Notification extends ServerMessage {
    String notification;
    public Notification(ServerMessageType type, String notification) {
        super(type);
        this.notification = notification;
    }
}
