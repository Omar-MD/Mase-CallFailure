package com.tus.cipher.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.exceptions.ApiError;
import com.tus.cipher.exceptions.ApiResponse;

@RestController
public class QueriesController {
	
	@GetMapping("/imsi-failures/{imsi}")
	public ApiResponse<Object> findImsiFailures(@PathVariable("imsi") long imsi) {
		ApiError error = ApiError.of("Error not implemented", "Not implemented");
		return ApiResponse.error(HttpStatus.NOT_IMPLEMENTED.value(), error);
	}
}
