package com.inTrack.spring.exception;

/**
 * @author vijayan
 */

public class ApplicationError extends RuntimeException {

    public ApplicationError(String message) {
        super(message);
    }
}
