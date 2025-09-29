package com.rentalapi.api.exception;

/**
 * Exception thrown for authentication-related errors in the rental API.
 * This is a runtime exception that is used to handle various authentication failures
 * such as invalid credentials, expired tokens, or unauthorized access attempts.
 */

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
