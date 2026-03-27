package com.nexuscart.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom Exception for Unauthorized Access Errors
 */
@Getter
public class UnauthorizedException extends RuntimeException {
    
    private final HttpStatus status;

    public UnauthorizedException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }
}
