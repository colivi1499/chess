package webSocketMessages.userCommands;

import chess.ChessGame;

public class RedrawBoard extends UserGameCommand {
    int gameId;

    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    ChessGame.TeamColor teamColor;

    public int getGameId() {
        return gameId;
    }

    public RedrawBoard(String authToken, int gameId, ChessGame.TeamColor teamColor) {
        super(authToken);
        this.gameId = gameId;
        this.commandType = CommandType.REDRAW_BOARD;
        this.teamColor = teamColor;
    }
}
