package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KnightMovesCalculator implements PieceMovesCalculator {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int width= myPosition.getRow();
        int height = myPosition.getColumn() ;
        if (width < 7 && height < 8) {
            width += 2;
            height += 1;
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
        if (width < 8 && height < 7) {
            width += 1;
            height += 2;
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
        if (width > 1 && height < 7) {
            width -= 1;
            height += 2;
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
        if (width > 2 && height < 8) {
            width -= 2;
            height += 1;
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
        if (width > 2 && height > 1) {
            width -= 2;
            height -= 1;
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
        if (width > 1 && height > 2) {
            width -= 1;
            height -= 2;
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
        if (width < 7 && height > 1) {
            width += 2;
            height -= 1;
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
        if (width < 8 && height > 2) {
            width += 1;
            height -= 2;
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


