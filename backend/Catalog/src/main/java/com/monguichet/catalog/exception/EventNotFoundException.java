package com.monguichet.catalog.exception;

public class EventNotFoundException extends RuntimeException
{
    public EventNotFoundException(String message) {
        super(message);
    }
}
