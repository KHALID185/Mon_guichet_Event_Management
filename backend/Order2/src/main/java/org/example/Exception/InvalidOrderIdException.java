package org.example.Exception;

public class InvalidOrderIdException extends RuntimeException {
    public InvalidOrderIdException(String s) {
        super(s);
    }
}