package com.llibron.projectplan.exceptions.custom;

import java.time.LocalDateTime;

public class ResourceNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Resource not found.";
    private final LocalDateTime timestamp;

    public ResourceNotFoundException() {
        super(DEFAULT_MESSAGE);
        this.timestamp = LocalDateTime.now();
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.timestamp = LocalDateTime.now();
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.timestamp = LocalDateTime.now();
    }

    public ResourceNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() { return timestamp; }
}
