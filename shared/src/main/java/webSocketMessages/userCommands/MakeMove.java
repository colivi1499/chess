package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand {
    public int getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }

    int gameID;
    ChessMove move;
    public MakeMove(String authToken, int gameID, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
        this.commandType = CommandType.MAKE_MOVE;
    }
}
