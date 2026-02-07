package com.jotomo.casino.exceptions;

public class ServiceNotFoundException extends Exception {
    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    public ServiceNotFoundException(String message) {
        super(message);
    }
}
