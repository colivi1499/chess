package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        if (row < 7 && col < 8) {
            row += 2;
            col++;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        row = myPosition.getRow();
        col = myPosition.getColumn();
        if (row < 8 && col < 7) {
            row++;
            col += 2;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        row = myPosition.getRow();
        col = myPosition.getColumn();
        if (row > 1 && col < 7) {
            row--;
            col += 2;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        row = myPosition.getRow();
        col = myPosition.getColumn();
        if (row > 2 && col < 8) {
            row -= 2;
            col++;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        row = myPosition.getRow();
        col = myPosition.getColumn();
        if (row > 2 && col > 1) {
            row -= 2;
            col--;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        row = myPosition.getRow();
        col = myPosition.getColumn();
        if (row > 1 && col > 2) {
            row--;
            col -= 2;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        row = myPosition.getRow();
        col = myPosition.getColumn();
        if (row < 8 && col > 2) {
            row++;
            col -= 2;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        row = myPosition.getRow();
        col = myPosition.getColumn();
        if (row < 7 && col > 1) {
            row += 2;
            col--;
            ChessPosition positionToCheck = new ChessPosition(row,col);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        return moves;
    }
}
