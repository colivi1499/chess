package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand {
    int gameID;
    ChessGame.TeamColor playerColor;
    public JoinPlayer(String authToken, int gameID, ChessGame.TeamColor color) {
        super(authToken);
        this.gameID = gameID;
        this.playerColor = color;
        this.commandType = CommandType.JOIN_PLAYER;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getTeamColor() {
        return playerColor;
    }

    public String getTeamColorString() {
        if (playerColor == null)
            return "";
        else if (playerColor.equals(ChessGame.TeamColor.BLACK))
            return "BLACK";
        else if (playerColor.equals(ChessGame.TeamColor.WHITE))
            return "WHITE";
        else return "";
    }
}
