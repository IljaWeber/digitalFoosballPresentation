package com.valtech.digitalFoosball.exceptions;

public class NameDuplicateException extends RuntimeException {
    public NameDuplicateException(String message) {
        super(message);
    }
}