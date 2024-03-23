package ui;

import chess.ChessBoard;
import chess.ChessGame;
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
                ChessPiece pieceToCheck = chessBoard.getPiece(new ChessPosition(i + 1,8 - j));
                boolean isWhite = false;
                if (pieceToCheck != null) {
                    if (pieceToCheck.getTeamColor() == ChessGame.TeamColor.WHITE)
                        isWhite = true;
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
                    board[i][j] = drawWhiteSquare(piece, isWhite);
                else
                    board[i][j] = drawBlackSquare(piece, isWhite);
            }
        }
    }

    public String printBoard(boolean blackView) {
        String result = "";
        if (blackView) {
            for (int i = 0; i < 8; i++) {
                result += String.format("%s%s%d ", SET_BG_COLOR_BLACK, SET_TEXT_COLOR_WHITE, i + 1);
                for (int j = 0; j < 8; j++) {
                    result += board[i][j];
                }
                result += SET_BG_COLOR_BLACK + "\n";
            }
            result += String.format("%s   h  g  f  e  d  c  b  a\n", SET_TEXT_COLOR_WHITE);
        } else {
            for (int i = 7; i >= 0; i--) {
                result += String.format("%s%s%d ", SET_BG_COLOR_BLACK, SET_TEXT_COLOR_WHITE, i + 1);
                for (int j = 7; j >= 0; j--) {
                    result += board[i][j];
                }
                result += SET_BG_COLOR_BLACK + "\n";
            }
            result += String.format("%s   a  b  c  d  e  f  g  h\n", SET_TEXT_COLOR_WHITE);
        }
        return result;
    }

    private String drawWhiteSquare(String piece, boolean white) {
        if (white)
            return String.format("%s %s%s ", SET_BG_COLOR_WHITE, SET_TEXT_COLOR_RED, piece);
        else
            return String.format("%s %s%s ", SET_BG_COLOR_WHITE, SET_TEXT_COLOR_BLACK, piece);
    }

    private String drawBlackSquare(String piece, boolean white) {
        if (white)
            return String.format("%s %s%s ", SET_BG_COLOR_LIGHT_GREY, SET_TEXT_COLOR_RED, piece);
        else
            return String.format("%s %s%s ", SET_BG_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLACK, piece);
    }
}
