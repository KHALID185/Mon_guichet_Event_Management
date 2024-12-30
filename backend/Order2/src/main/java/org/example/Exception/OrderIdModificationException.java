package org.example.Exception;

public class OrderIdModificationException extends RuntimeException {
    public OrderIdModificationException(String message) {
        super(message);
    }
}