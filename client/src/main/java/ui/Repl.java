package ui;


import chess.ChessGame;
import dataAccess.DataAccessException;
import spark.Spark;
import webSocketFacade.NotificationHandler;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.LoadGameHighlight;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl implements NotificationHandler {
    private final ChessClient client;

    public Repl(int port) {
        client = new ChessClient(port, this);
    }

    public void run() {
        System.out.println(BLACK_PAWN + " Welcome to 240 chess. Type Help to get started. " + BLACK_PAWN);

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Exception e) {
                var msg = e.getMessage();
                System.out.print(msg);
            }
        }
        Spark.stop();
        Spark.awaitStop();
        System.out.println();
    }


    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + SET_BG_COLOR_DARK_GREY + ">>> " + SET_TEXT_COLOR_GREEN);
    }

    @Override
    public void notify(ServerMessage serverMessage) {
        if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
            ChessBoardUI boardUI = new ChessBoardUI(((LoadGame) serverMessage).getGame().getBoard());
            String board = "";
            if (((LoadGame) serverMessage).getColor() == ChessGame.TeamColor.WHITE)
                board = boardUI.printBoard(false);
            else if (((LoadGame) serverMessage).getColor() == ChessGame.TeamColor.BLACK)
                board = boardUI.printBoard(true);
            System.out.println(String.format("\n%s",board));
        } else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME_HIGHLIGHT) {
            ChessBoardUI boardUI = new ChessBoardUI(((LoadGameHighlight) serverMessage).getGame().getBoard());
            String board = "";
            if (((LoadGameHighlight) serverMessage).getColor() == ChessGame.TeamColor.WHITE)
                board = boardUI.printBoardHighlight(false,((LoadGameHighlight) serverMessage).getPosition());
            else if (((LoadGameHighlight) serverMessage).getColor() == ChessGame.TeamColor.BLACK)
                board = boardUI.printBoardHighlight(true,((LoadGameHighlight) serverMessage).getPosition());
            System.out.println(String.format("\n%s",board));
        } else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
            System.out.println("\n" + SET_TEXT_COLOR_MAGENTA + ((Notification) serverMessage).getMessage());
            printPrompt();
        } else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
            System.out.println("\n" + SET_TEXT_COLOR_RED + ((webSocketMessages.serverMessages.Error) serverMessage).getErrorMessage());
            printPrompt();
        } else {
            System.out.println("\n" + SET_TEXT_COLOR_RED + ((webSocketMessages.serverMessages.Error) serverMessage).getErrorMessage());
            printPrompt();
        }
    }
}
