package webSocketMessages.userCommands;

public class Resign extends UserGameCommand {
    public int getGameID() {
        return gameID;
    }

    int gameID;
    public Resign(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
    }
}
