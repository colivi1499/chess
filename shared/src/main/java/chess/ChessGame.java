package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame{

    private TeamColor teamTurn = TeamColor.WHITE;
    private ChessBoard board;

    public ChessGame() {
        this(new ChessBoard());
    }

    public ChessGame(ChessBoard board) {
        this.board = board;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }


    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        if (board.getPiece(startPosition) == null) {
            return validMoves;
        }
        TeamColor color = board.getPiece(startPosition).getTeamColor();
        ChessBoard newBoard = (ChessBoard) board.clone();
        for (ChessMove move : board.getPiece(startPosition).pieceMoves(board,startPosition)) {
            newBoard = (ChessBoard) board.clone();

            if (!new ChessGame(newBoard).movePiece(newBoard,move).isInCheck(color)) {
                validMoves.add(move);
            }

        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game without checking for checks
     *
     * @param move chess move to preform
     * @return board after move has been made
     */
    public ChessGame movePiece(ChessBoard board, ChessMove move) {
        board.addPiece(move.getEndPosition(),board.getPiece(move.getStartPosition()));
        board.removePiece(move.getStartPosition());
        return new ChessGame(board);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition king = null;
        ChessPosition positionToCheck;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                positionToCheck = new ChessPosition(i,j);
                if (board.getPiece(positionToCheck) != null && board.getPiece(positionToCheck).getTeamColor().equals(teamColor)) {
                    if (board.getPiece(positionToCheck).getPieceType().equals(ChessPiece.PieceType.KING)) {
                        king = positionToCheck;
                    }
                }
            }
        }

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                positionToCheck = new ChessPosition(i,j);
                if (board.getPiece(positionToCheck) != null && board.getPiece(positionToCheck).getTeamColor() != teamColor) {
                    for (ChessMove move : board.getPiece(positionToCheck).pieceMoves(board,positionToCheck)) {
                        if (move.getEndPosition().equals(king)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "teamTurn=" + teamTurn +
                ", board=" + board +
                '}';
    }
}
