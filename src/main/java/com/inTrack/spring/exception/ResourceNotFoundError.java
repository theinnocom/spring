package com.inTrack.spring.exception;

/**
 * @author vijayan
 */

public class ResourceNotFoundError extends ApplicationError {

    public ResourceNotFoundError(String message) {
        super(message);
    }
}
