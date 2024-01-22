package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        switch(type) {
            case KING:
                KingMovesCalculator kingCalculator = new KingMovesCalculator();
                return kingCalculator.pieceMoves(board,myPosition);
            case QUEEN:
                QueenMovesCalculator queenCalculator = new QueenMovesCalculator();
                return queenCalculator.pieceMoves(board,myPosition);
            case BISHOP:
                BishopMovesCalculator bishopCalculator = new BishopMovesCalculator();
                return bishopCalculator.pieceMoves(board,myPosition);
            case KNIGHT:
                KnightMovesCalculator knightCalculator = new KnightMovesCalculator();
                return knightCalculator.pieceMoves(board,myPosition);
            case ROOK:
                RookMovesCalculator rookCalculator = new RookMovesCalculator();
                return rookCalculator.pieceMoves(board,myPosition);
            case PAWN:
                PawnMovesCalculator pawnCalculator = new PawnMovesCalculator();
                return pawnCalculator.pieceMoves(board,myPosition);
            default:
                System.out.print("Invalid piece type");
        }

        return new ArrayList<>();
    }
}
