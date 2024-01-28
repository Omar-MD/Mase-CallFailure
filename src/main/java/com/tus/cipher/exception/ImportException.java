package com.tus.cipher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImportException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ImportException(String msg) {
		super(msg);
	}
}
