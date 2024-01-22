package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KingMovesCalculator implements PieceMovesCalculator {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int width= myPosition.getRow();
        int height = myPosition.getColumn() ;
        if (width < 8 && height < 8) {
            width++;
            height++;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        width= myPosition.getRow();
        height = myPosition.getColumn() ;
        if (height < 8) {
            height++;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        width= myPosition.getRow();
        height = myPosition.getColumn() ;
        if (width > 1 && height < 8) {
            width--;
            height++;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        width= myPosition.getRow();
        height = myPosition.getColumn() ;
        if (width > 1) {
            width--;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        width= myPosition.getRow();
        height = myPosition.getColumn() ;
        if (width > 1 && height > 1) {
            width--;
            height--;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        width= myPosition.getRow();
        height = myPosition.getColumn() ;
        if (height > 1) {
            height--;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        width= myPosition.getRow();
        height = myPosition.getColumn() ;
        if (width < 8 && height > 1) {
            width++;
            height--;
            ChessPosition positionToCheck = new ChessPosition(width,height);
            if (board.getPiece(positionToCheck) == null) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
            else if (board.getPiece(positionToCheck).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                moves.add(new ChessMove(myPosition,positionToCheck));
            }
        }

        width= myPosition.getRow();
        height = myPosition.getColumn() ;
        if (width < 8) {
            width++;
            ChessPosition positionToCheck = new ChessPosition(width,height);
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

