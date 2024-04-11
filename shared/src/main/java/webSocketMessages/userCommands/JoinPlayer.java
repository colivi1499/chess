package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand {
    int gameID;
    ChessGame.TeamColor teamColor;
    public JoinPlayer(String authToken, int gameID, ChessGame.TeamColor color) {
        super(authToken);
        this.gameID = gameID;
        this.teamColor = color;
        this.commandType = CommandType.JOIN_PLAYER;
    }
}
