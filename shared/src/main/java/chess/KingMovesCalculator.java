package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        if (row < 8 && col < 8) {
            row++;
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
        if (row < 8) {
            row++;
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
        if (row < 8 && col > 1) {
            row++;
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
        if (row > 1 && col > 1) {
            row--;
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
        if (col < 8) {
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
        if (col > 1) {
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
        if (row > 1 && col < 8) {
            row--;
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
        if (row > 1) {
            row--;
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
