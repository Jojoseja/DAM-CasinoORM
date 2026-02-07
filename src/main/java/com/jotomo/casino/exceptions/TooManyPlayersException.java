package com.jotomo.casino.exceptions;

public class TooManyPlayersException extends Exception {
    public TooManyPlayersException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyPlayersException(String message) {
        super(message);
    }
}
