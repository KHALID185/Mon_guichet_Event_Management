package org.example.Exception;

public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException() {
        super("Invalid date range: Start date must be on or before the end date.");
    }
}