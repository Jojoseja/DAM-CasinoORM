package com.jotomo.casino.exceptions;

public class ClientNotFoundException extends Exception {
    public ClientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientNotFoundException(String message) {
        super(message);
    }
}
