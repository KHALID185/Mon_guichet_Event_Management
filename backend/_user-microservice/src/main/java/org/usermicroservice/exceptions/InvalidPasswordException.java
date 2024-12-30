package org.usermicroservice.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;

    public InvalidPasswordException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
