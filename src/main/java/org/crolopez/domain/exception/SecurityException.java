package org.crolopez.domain.exception;

public class SecurityException extends BusinessException {
    public SecurityException(String code) {
        super("Security violation detected", code);
    }
} 