package com.jotomo.casino.exceptions;

public class ServiceAlreadyExistsException extends Exception {
    public ServiceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceAlreadyExistsException(String message) {
        super(message);
    }
}
