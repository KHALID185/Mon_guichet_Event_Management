package org.usermicroservice.exceptions;

public class EmptyEntityException extends RuntimeException{
    public EmptyEntityException(String message) {
        super(message);
    }
}
