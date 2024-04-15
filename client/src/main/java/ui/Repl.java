package ui;


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
        System.out.println();
    }


    private void printPrompt() {
        System.out.print("\n\u001B[0m" + ">>> " + SET_TEXT_COLOR_GREEN);
    }

    @Override
    public void notify(ServerMessage serverMessage) {
        switch (serverMessage.getServerMessageType()) {
            case LOAD_GAME:
                ChessBoardUI boardUI = new ChessBoardUI(((LoadGame) serverMessage).getGame().getBoard());
                String board = switch (client.getCurrentColor()) {
                    case WHITE -> boardUI.printBoard(false);
                    case BLACK -> boardUI.printBoard(true);
                    case null -> boardUI.printBoard(false);

                };
                System.out.printf("\n%s%n", board);
                break;
            case LOAD_GAME_HIGHLIGHT:
                ChessBoardUI boardUIHighlight = new ChessBoardUI(((LoadGameHighlight) serverMessage).getGame().getBoard());
                String boardHighlight = switch (((LoadGameHighlight) serverMessage).getColor()) {
                    case WHITE ->
                            boardUIHighlight.printBoardHighlight(false, ((LoadGameHighlight) serverMessage).getPosition());
                    case BLACK ->
                            boardUIHighlight.printBoardHighlight(true, ((LoadGameHighlight) serverMessage).getPosition());
                };
                System.out.printf("\n%s%n", boardHighlight);
                break;
            case NOTIFICATION:
                System.out.println("\n" + SET_TEXT_COLOR_MAGENTA + ((Notification) serverMessage).getMessage());
                printPrompt();
                break;
            case ERROR:
                System.out.println("\n" + SET_TEXT_COLOR_RED + ((webSocketMessages.serverMessages.Error) serverMessage).getErrorMessage());
                printPrompt();
                break;
            default:
                System.out.println("\n" + SET_TEXT_COLOR_RED + serverMessage);
                printPrompt();
        }

    }
}
