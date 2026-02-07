package com.jotomo.casino.exceptions;

public class ValidacionException extends RuntimeException {
    public ValidacionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidacionException(String message) {
        super(message);
    }
}
