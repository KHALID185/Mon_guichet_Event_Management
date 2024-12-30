package org.usermicroservice.exceptions.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.usermicroservice.dtos.ErrorBodyDTO;
import org.usermicroservice.exceptions.*;
import java.util.Collections;
import java.util.Date;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotValidException.class)
    public ResponseEntity<?> handleAlreadyExistsException(DataNotValidException ex) {
        ErrorBodyDTO errorBodyDTO = new ErrorBodyDTO(new Date(), HttpStatus.NOT_ACCEPTABLE, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBodyDTO, errorBodyDTO.getStatus());
    }

    @ExceptionHandler(EmailAlreadyExist.class)
    public ResponseEntity<?> handleUserNotFound(EmailAlreadyExist ex) {
        ErrorBodyDTO errorBodyDTO = new ErrorBodyDTO(new Date(), HttpStatus.NOT_FOUND, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBodyDTO, errorBodyDTO.getStatus());
    }

    @ExceptionHandler(value = {EmptyEntityException.class,InvalidPasswordException.class})
    public ResponseEntity<?> handleAlreadyExistsException(RuntimeException ex) {
        ErrorBodyDTO errorBodyDTO = new ErrorBodyDTO(new Date(), HttpStatus.NOT_ACCEPTABLE, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBodyDTO, errorBodyDTO.getStatus());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(RoleNotFoundException ex) {
        ErrorBodyDTO errorBodyDTO = new ErrorBodyDTO(new Date(), HttpStatus.NOT_FOUND, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBodyDTO, errorBodyDTO.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        ErrorBodyDTO errorBodyDTO = new ErrorBodyDTO(new Date(), HttpStatus.NOT_FOUND, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorBodyDTO, errorBodyDTO.getStatus());
    }

}
