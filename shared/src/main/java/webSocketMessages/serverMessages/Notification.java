package webSocketMessages.serverMessages;

public class Notification extends ServerMessage {
    public Notification(String notification) {
        super(ServerMessageType.NOTIFICATION);
        this.notification = notification;
    }
}
