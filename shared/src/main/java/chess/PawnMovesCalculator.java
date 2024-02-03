package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (row == 2) {
                row++;
                ChessPosition positionToCheck = new ChessPosition(row,col);
                ChessPosition positionToCheck2 = new ChessPosition(row+1,col);
                if (board.getPiece(positionToCheck) == null && board.getPiece(positionToCheck2) == null) {
                    moves.add(new ChessMove(myPosition,positionToCheck2));
                }
                row = myPosition.getRow();
            }
            if (row == 7) {
                row++;
                ChessPosition positionToCheck = new ChessPosition(row,col);
                if (board.getPiece(positionToCheck) == null) {
                    moves.add(new ChessMove(myPosition,positionToCheck, ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition,positionToCheck, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition,positionToCheck, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition,positionToCheck, ChessPiece.PieceType.BISHOP));
                }
                if (col > 1) {
                    ChessPosition leftCapture = new ChessPosition(row, col-1);
                    if (board.getPiece(leftCapture) != null && board.getPiece(leftCapture).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        moves.add(new ChessMove(myPosition,leftCapture, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition,leftCapture, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition,leftCapture, ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(myPosition,leftCapture, ChessPiece.PieceType.BISHOP));
                    }
                }
                if (col < 8) {
                    ChessPosition rightCapture = new ChessPosition(row, col+1);
                    if (board.getPiece(rightCapture) != null && board.getPiece(rightCapture).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        moves.add(new ChessMove(myPosition,rightCapture, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition,rightCapture, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition,rightCapture, ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(myPosition,rightCapture, ChessPiece.PieceType.BISHOP));
                    }
                }
            }
            else if (row < 8) {
                row++;
                ChessPosition positionToCheck = new ChessPosition(row,col);
                if (board.getPiece(positionToCheck) == null) {
                    moves.add(new ChessMove(myPosition,positionToCheck));
                }
                if (col > 1) {
                    ChessPosition leftCapture = new ChessPosition(row, col-1);
                    if (board.getPiece(leftCapture) != null && board.getPiece(leftCapture).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        moves.add(new ChessMove(myPosition,leftCapture));
                    }
                }
                if (col < 8) {
                    ChessPosition rightCapture = new ChessPosition(row, col+1);
                    if (board.getPiece(rightCapture) != null && board.getPiece(rightCapture).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        moves.add(new ChessMove(myPosition,rightCapture));
                    }
                }

            }
        } else {
            if (row == 7) {
                row--;
                ChessPosition positionToCheck = new ChessPosition(row,col);
                ChessPosition positionToCheck2 = new ChessPosition(row-1,col);
                if (board.getPiece(positionToCheck) == null && board.getPiece(positionToCheck2) == null) {
                    moves.add(new ChessMove(myPosition,positionToCheck2));
                }
                row = myPosition.getRow();
            }
            if (row == 2) {
                row--;
                ChessPosition positionToCheck = new ChessPosition(row,col);
                if (board.getPiece(positionToCheck) == null) {
                    moves.add(new ChessMove(myPosition,positionToCheck, ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition,positionToCheck, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition,positionToCheck, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition,positionToCheck, ChessPiece.PieceType.BISHOP));
                }
                if (col > 1) {
                    ChessPosition leftCapture = new ChessPosition(row, col-1);
                    if (board.getPiece(leftCapture) != null && board.getPiece(leftCapture).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        moves.add(new ChessMove(myPosition,leftCapture, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition,leftCapture, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition,leftCapture, ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(myPosition,leftCapture, ChessPiece.PieceType.BISHOP));
                    }
                }
                if (col < 8) {
                    ChessPosition rightCapture = new ChessPosition(row, col+1);
                    if (board.getPiece(rightCapture) != null && board.getPiece(rightCapture).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        moves.add(new ChessMove(myPosition,rightCapture, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition,rightCapture, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition,rightCapture, ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(myPosition,rightCapture, ChessPiece.PieceType.BISHOP));
                    }
                }
            }
            else if (row > 1) {
                row--;
                ChessPosition positionToCheck = new ChessPosition(row,col);
                if (board.getPiece(positionToCheck) == null) {
                    moves.add(new ChessMove(myPosition,positionToCheck));
                }
                if (col > 1) {
                    ChessPosition leftCapture = new ChessPosition(row, col-1);
                    if (board.getPiece(leftCapture) != null && board.getPiece(leftCapture).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        moves.add(new ChessMove(myPosition,leftCapture));
                    }
                }
                if (col < 8) {
                    ChessPosition rightCapture = new ChessPosition(row, col+1);
                    if (board.getPiece(rightCapture) != null && board.getPiece(rightCapture).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        moves.add(new ChessMove(myPosition,rightCapture));
                    }
                }

            }
        }

        return moves;
    }
}
