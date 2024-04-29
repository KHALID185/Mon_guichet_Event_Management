package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends RuntimeException{
    public static final String USER_ALREADY_EXISTS = "The user already exists";
    public UserAlreadyExistsException(String message){
        super(message);
    }
}