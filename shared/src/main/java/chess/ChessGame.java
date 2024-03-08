package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame{

    private TeamColor teamTurn = TeamColor.WHITE;
    private ChessBoard board;

    private boolean whiteKingMoved = false;
    private boolean whiteRookRightMoved = false;
    private boolean whiteRookLeftMoved = false;
    private boolean blackKingMoved = false;
    private boolean blackRookRightMoved = false;
    private boolean blackRookLeftMoved = false;


    public ChessGame() {
        this(new ChessBoard());
    }

    public ChessGame(ChessBoard board) {
        this.board = board;
        if (board.getPiece(new ChessPosition(1,5)) != null)
            whiteKingMoved = !(board.getPiece(new ChessPosition(1,5)).equals(new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING)));
        if (board.getPiece(new ChessPosition(1,8)) != null)
            whiteRookRightMoved = !(board.getPiece(new ChessPosition(1,8)).equals(new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.ROOK)));
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
        ChessBoard newBoard;
        for (ChessMove move : board.getPiece(startPosition).pieceMoves(board,startPosition)) {
            newBoard = board.clone();
            if (!new ChessGame(newBoard).movePiece(newBoard,move).isInCheck(color)) {
                validMoves.add(move);
            }
        }

        if (startPosition.equals(new ChessPosition(1,5))) {
            if (!whiteKingMoved && board.getPiece(startPosition).equals(new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING))) {
                if (!whiteRookRightMoved && board.getPiece(new ChessPosition(1,6)) == null && board.getPiece(new ChessPosition(1,7)) == null) {
                    if (!isInCheck(TeamColor.WHITE)) {
                        ChessPosition position;
                        boolean moveThroughCheck = false;
                        for (int i = 1; i <= 8; i++) {
                            for (int j = 1; j <= 8; j++) {
                                position = new ChessPosition(i,j);
                                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() == TeamColor.BLACK) {
                                    for (ChessMove move : board.getPiece(position).pieceMoves(board,position)) {
                                        if (move.getEndPosition().equals(new ChessPosition(1, 6)) || move.getEndPosition().equals(new ChessPosition(1, 7))) {
                                            moveThroughCheck = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (!moveThroughCheck) {
                            validMoves.add(new ChessMove(new ChessPosition(1,5),new ChessPosition(1,7)));
                        }
                    }
                }
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
        if (getTeamTurn() == board.getPiece(move.getStartPosition()).getTeamColor() && validMoves(move.getStartPosition()).contains(move)) {
            if (move.getPromotionPiece() != null) {
                board.addPiece(move.getEndPosition(),new ChessPiece(getTeamTurn(),move.getPromotionPiece()));
            } else {
                board.addPiece(move.getEndPosition(),board.getPiece(move.getStartPosition()));
                if (board.getPiece(move.getEndPosition()).equals(new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING))) {
                    whiteKingMoved = true;
                    if (move.equals(new ChessMove(new ChessPosition(1,5),new ChessPosition(1,7),null))) {
                        board.addPiece(new ChessPosition(1,6),new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.ROOK));
                        board.removePiece(new ChessPosition(1,8));
                    }
                }
                if (board.getPiece(move.getEndPosition()).equals(new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.ROOK))) {
                    if (move.getStartPosition().equals(new ChessPosition(1,8)))
                        whiteRookRightMoved = true;
                    if (move.getStartPosition().equals(new ChessPosition(1,1)))
                        whiteRookLeftMoved = true;
                }
            }
            board.removePiece(move.getStartPosition());
            if (getTeamTurn() == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            } else {
                setTeamTurn(TeamColor.WHITE);
            }
        } else {
            throw new InvalidMoveException("Invalid move");
        }
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
        ChessPosition position;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                position = new ChessPosition(i,j);
                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() == teamColor && !validMoves(position).isEmpty()) {
                    return false;
                }
            }
        }
        return (isInCheck(teamColor));
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ChessPosition position;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                position = new ChessPosition(i,j);
                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() == teamColor && !validMoves(position).isEmpty()) {
                    return false;
                }
            }
        }
        return (!isInCheck(teamColor));
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
        if (board.getPiece(new ChessPosition(1,5)) != null)
            whiteKingMoved = !(board.getPiece(new ChessPosition(1,5)).equals(new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING)));
        if (board.getPiece(new ChessPosition(1,8)) != null)
            whiteRookRightMoved = !(board.getPiece(new ChessPosition(1,8)).equals(new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.ROOK)));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return whiteKingMoved == chessGame.whiteKingMoved && whiteRookRightMoved == chessGame.whiteRookRightMoved && whiteRookLeftMoved == chessGame.whiteRookLeftMoved && blackKingMoved == chessGame.blackKingMoved && blackRookRightMoved == chessGame.blackRookRightMoved && blackRookLeftMoved == chessGame.blackRookLeftMoved && teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board, whiteKingMoved, whiteRookRightMoved, whiteRookLeftMoved, blackKingMoved, blackRookRightMoved, blackRookLeftMoved);
    }
}
