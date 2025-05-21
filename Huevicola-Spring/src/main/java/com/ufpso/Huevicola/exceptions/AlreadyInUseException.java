package com.ufpso.Huevicola.exceptions;

public class AlreadyInUseException extends RuntimeException {
    public AlreadyInUseException(String message) {
        super(message);
    }
}
