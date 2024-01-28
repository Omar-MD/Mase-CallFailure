package com.tus.cipher.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) { 
        super(message);
    }
}
