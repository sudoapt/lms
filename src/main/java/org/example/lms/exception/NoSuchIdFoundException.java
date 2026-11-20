package org.example.lms.exception;

public class NoSuchIdFoundException extends RuntimeException {
    public NoSuchIdFoundException(String message) {
        super(message);
    }
}
