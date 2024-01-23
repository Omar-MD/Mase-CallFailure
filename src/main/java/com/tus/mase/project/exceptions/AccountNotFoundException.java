package com.tus.mase.project.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) { 
        super(message);
    }
}
