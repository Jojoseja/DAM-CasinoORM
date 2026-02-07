package com.jotomo.casino.exceptions;

public class AccesoDenegadoException extends Exception {
    public AccesoDenegadoException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccesoDenegadoException(String message) {
        super(message);
    }
}
