package com.tus.cipher.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedAccessException extends RuntimeException {
    private static final long serialVersionUID = -2713353358156596441L;

	public UnauthorizedAccessException(String message) {
        super(message);
    }
}