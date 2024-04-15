package ui;

import chess.ChessPiece;
import chess.ChessPosition;

public class PieceConverter {
    public ChessPiece.PieceType convertPiece(String piece) throws ArgumentException {
        return switch (piece) {
            case "queen" -> ChessPiece.PieceType.QUEEN;
            case "rook" -> ChessPiece.PieceType.ROOK;
            case "knight" -> ChessPiece.PieceType.KNIGHT;
            case "bishop" -> ChessPiece.PieceType.BISHOP;
            default -> throw new ArgumentException("Invalid promotion piece");
        };
    }
}
