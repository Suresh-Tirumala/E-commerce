package com.nexuscart.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom Exception for Bad Request Errors
 */
@Getter
public class BadRequestException extends RuntimeException {
    
    private final HttpStatus status;

    public BadRequestException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
