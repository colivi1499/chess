package ui;

import chess.ChessPosition;

public class PositionConverter {
    public ChessPosition convertPosition(String position) throws ArgumentException {
        if (position.length() != 2) {
            throw new ArgumentException("Invalid move input");
        }
        char file = position.charAt(0);
        char rank = position.charAt(1);

        int col = file - 'a' + 1;
        int row = rank - '1' + 1;

        if (row < 1 || row > 8 || col < 1 || col > 8) {
            throw new ArgumentException("Invalid positions");
        }

        return new ChessPosition(row, col);
    }
}
