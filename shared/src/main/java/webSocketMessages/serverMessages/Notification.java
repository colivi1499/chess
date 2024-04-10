package webSocketMessages.serverMessages;

public class Notification extends ServerMessage {
    String notification;
    public Notification(String notification) {
        super(ServerMessageType.NOTIFICATION);
        this.notification = notification;
    }
}
