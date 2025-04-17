package com.inTrack.spring.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author vijayan
 */

public class SecurityError extends AuthenticationException {

    public SecurityError(String message) {
        super(message);
    }
}
