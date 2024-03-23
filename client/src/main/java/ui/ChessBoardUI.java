package ui;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;

public class ChessBoardUI {
    private String[][] board = new String[8][8];
    ChessBoard chessBoard;
    public ChessBoardUI(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece = " ";
                ChessPiece pieceToCheck = chessBoard.getPiece(new ChessPosition(i + 1,j + 1));
                if (pieceToCheck != null) {
                    switch(pieceToCheck.getPieceType()) {
                        case ChessPiece.PieceType.KING:
                            piece = "K";
                            break;
                        case ChessPiece.PieceType.BISHOP:
                            piece = "B";
                            break;
                        case ChessPiece.PieceType.PAWN:
                            piece = "P";
                            break;
                        case ChessPiece.PieceType.QUEEN:
                            piece = "Q";
                            break;
                        case ChessPiece.PieceType.ROOK:
                            piece = "R";
                            break;
                        case ChessPiece.PieceType.KNIGHT:
                            piece = "N";
                            break;
                    }
                }
                if ((i + j) % 2 == 0)
                    board[i][j] = drawWhiteSquare(piece);
                else
                    board[i][j] = drawBlackSquare(piece);
            }
        }
    }

    public String printBoard() {
        String result = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                result += board[i][j];
            }
            result += SET_BG_COLOR_BLACK + "\n";
        }
        return result;
    }

    private String drawWhiteSquare(String piece) {
        return String.format("%s %s ", SET_BG_COLOR_WHITE, piece);
    }

    private String drawBlackSquare(String piece) {
        return String.format("%s %s ", SET_BG_COLOR_LIGHT_GREY, piece);
    }
}
