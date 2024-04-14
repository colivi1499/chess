package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessPosition;

public class Highlight extends UserGameCommand {
    int gameId;

    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    ChessGame.TeamColor teamColor;

    public ChessPosition getPosition() {
        return position;
    }

    ChessPosition position;
    public int getGameId() {
        return gameId;
    }

    public Highlight(String authToken, int gameId, ChessPosition position, ChessGame.TeamColor color) {
        super(authToken);
        this.gameId = gameId;
        this.commandType = CommandType.HIGHLIGHT;
        this.teamColor = color;
        this.position = position;
    }
}
