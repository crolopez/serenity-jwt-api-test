package org.crolopez.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super(String.format("User with ID %d was not found", userId));
    }

    public UserNotFoundException(String username) {
        super(String.format("User with username '%s' was not found", username));
    }
} 