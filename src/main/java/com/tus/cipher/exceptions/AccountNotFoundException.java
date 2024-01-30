package com.tus.cipher.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1526017017667558563L;

	public AccountNotFoundException(String message) {
        super(message);
    }
}
