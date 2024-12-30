package org.usermicroservice.exceptions;

public class DataNotValidException extends RuntimeException {
    public DataNotValidException(String message) {
        super(message);
    }
}
