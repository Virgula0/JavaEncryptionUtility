package Exceptions;

import Loggers.Logger;

import java.util.List;

public class Base64NotValid extends Exception {
    private static final long serialVersionUID = 7718828512143293558L;

    public Base64NotValid(String message, List<Logger> loggers) {
        super(message);
        loggers.forEach(x -> x.log(message));
    }
}
