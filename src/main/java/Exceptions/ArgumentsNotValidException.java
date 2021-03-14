package Exceptions;

public class ArgumentsNotValidException extends Exception {
    private static final long serialVersionUID = 7718828512143293558L;

    public ArgumentsNotValidException(String message) {
        super(message);
    }
}
