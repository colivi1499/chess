package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator implements PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        BishopMovesCalculator bishopCalculator = new BishopMovesCalculator();
        RookMovesCalculator rookCalculator = new RookMovesCalculator();
        moves.addAll(bishopCalculator.pieceMoves(board,myPosition));
        moves.addAll(rookCalculator.pieceMoves(board,myPosition));

        return moves;
    }
}
