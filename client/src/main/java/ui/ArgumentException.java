package ui;
/*
Indicates that the user provided the wrong number of arguments
*/

public class ArgumentException extends Exception {
    public ArgumentException(String message) {
        super(message);
    }
}
