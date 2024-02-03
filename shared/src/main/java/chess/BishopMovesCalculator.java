package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        while (row < 8 && col < 8) {
            row++;
            col++;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
                continue;
            }
            if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            break;
        }

        row = myPosition.getRow();
        col = myPosition.getColumn();
        while (row > 1 && col < 8) {
            row--;
            col++;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
                continue;
            }
            if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            break;
        }

        row = myPosition.getRow();
        col = myPosition.getColumn();
        while (row < 8 && col > 1) {
            row++;
            col--;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
                continue;
            }
            if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            break;
        }

        row = myPosition.getRow();
        col = myPosition.getColumn();
        while (row > 1 && col > 1) {
            row--;
            col--;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
                continue;
            }
            if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            break;
        }

        return moves;
    }
}
