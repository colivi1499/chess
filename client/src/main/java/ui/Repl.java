package ui;


import dataAccess.DataAccessException;
import spark.Spark;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
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
            } catch (DataAccessException | ArgumentException e) {
                var msg = e.getMessage();
                System.out.print(msg);
            }
        }
        System.out.println();
    }


    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
    }

}
