package webSocketMessages.serverMessages;

import chess.ChessGame;
import chess.ChessPosition;

public class LoadGameHighlight extends ServerMessage {
    public ChessGame getGame() {
        return game;
    }

    public ChessGame.TeamColor getColor() {
        return color;
    }

    ChessGame game;
    ChessGame.TeamColor color;

    public ChessPosition getPosition() {
        return position;
    }

    ChessPosition position;
    public LoadGameHighlight(ChessGame game, ChessGame.TeamColor color, ChessPosition position) {
        super(ServerMessageType.LOAD_GAME_HIGHLIGHT);
        this.game = game;
        this.color = color;
        this.position = position;
    }
}

