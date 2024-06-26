package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinObserver extends UserGameCommand {

    int gameID;
    public JoinObserver(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.JOIN_OBSERVER;
    }

    public int getGameID() {
        return gameID;
    }
}
