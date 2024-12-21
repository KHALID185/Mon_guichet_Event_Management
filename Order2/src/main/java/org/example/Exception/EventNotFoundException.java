package org.example.Exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(Long productId) {
        super("event with id " + productId + " not found");
    }
}
