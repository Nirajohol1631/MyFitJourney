package com.cdac.exception;

import java.util.Date;

/**
 * A simple POJO (Plain Old Java Object) to structure the error response
 * that is sent back to the client when an exception occurs.
 */
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // Getters for all fields are needed for JSON serialization

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
