package com.cdac.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This custom exception is thrown when a requested resource is not found in the database.
 * The @ResponseStatus annotation ensures that Spring Boot responds with a 404 NOT_FOUND status code.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
