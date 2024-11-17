package com.llibron.projectplan.exceptions.custom;

import java.time.LocalDateTime;

public class InvalidTaskRequestException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid task.";
    private final LocalDateTime timestamp;

    public InvalidTaskRequestException() {
        super(DEFAULT_MESSAGE);
        this.timestamp = LocalDateTime.now();
    }

    public InvalidTaskRequestException(String message) {
        super(message);
        this.timestamp = LocalDateTime.now();
    }

    public InvalidTaskRequestException(String message, Throwable cause) {
        super(message, cause);
        this.timestamp = LocalDateTime.now();
    }

    public InvalidTaskRequestException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() { return timestamp; }
}
