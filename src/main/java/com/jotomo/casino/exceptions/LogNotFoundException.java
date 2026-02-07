package com.jotomo.casino.exceptions;

public class LogNotFoundException extends Exception {
    public LogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogNotFoundException(String message) {
        super(message);
    }
}
