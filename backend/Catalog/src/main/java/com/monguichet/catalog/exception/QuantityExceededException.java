package com.monguichet.catalog.exception;

public class QuantityExceededException extends RuntimeException{
    public QuantityExceededException(String message) {
        super(message);
    }
}