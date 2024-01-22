package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator implements PieceMovesCalculator {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int width= myPosition.getRow();
        int height = myPosition.getColumn() ;
        while (height < 8) {
            height++;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
                continue;
            }
            if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            break;
        }

        width= myPosition.getRow();
        height = myPosition.getColumn();
        while (width > 1) {
            width--;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
                continue;
            }
            if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            break;
        }

        width= myPosition.getRow();
        height = myPosition.getColumn();
        while (width < 8) {
            width++;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
                continue;
            }
            if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            break;
        }

        width= myPosition.getRow();
        height = myPosition.getColumn();
        while (height > 1) {
            height--;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
                continue;
            }
            if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            break;
        }

        width= myPosition.getRow();
        height = myPosition.getColumn();
        while (width < 8 && height < 8) {
            width++;
            height++;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
                continue;
            }
            if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            break;
        }

        width= myPosition.getRow();
        height = myPosition.getColumn();
        while (width > 1 && height < 8) {
            width--;
            height++;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
                continue;
            }
            if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            break;
        }

        width= myPosition.getRow();
        height = myPosition.getColumn();
        while (width < 8 && height > 1) {
            width++;
            height--;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
                continue;
            }
            if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            break;
        }

        width= myPosition.getRow();
        height = myPosition.getColumn();
        while (width > 1 && height > 1) {
            width--;
            height--;
            ChessPosition positionToCheck = new ChessPosition(width,height);
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
