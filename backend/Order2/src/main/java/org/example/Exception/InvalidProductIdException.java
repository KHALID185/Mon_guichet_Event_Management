package org.example.Exception;

public class InvalidProductIdException extends RuntimeException {
    public InvalidProductIdException(String s) { super(s);
    }
}