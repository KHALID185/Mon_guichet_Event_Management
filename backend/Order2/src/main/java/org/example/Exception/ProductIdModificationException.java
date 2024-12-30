package org.example.Exception;

public class ProductIdModificationException extends RuntimeException {
    public ProductIdModificationException(String message) {
        super(message);
    }
}