package com.inTrack.spring.exception;

/**
 * @author vijayan
 */

public class ValidationError extends ApplicationError {

    public ValidationError(String message) {
        super(message);
    }

}
