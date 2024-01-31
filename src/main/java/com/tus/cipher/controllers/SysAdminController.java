package com.tus.cipher.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dto.ImportRequest;
import com.tus.cipher.exceptions.ApiError;
import com.tus.cipher.exceptions.ApiResponse;
import com.tus.cipher.services.ImportService;

@RestController
@RequestMapping("/sysadmin")
public class SysAdminController {

	@Autowired
	ImportService importService;

	@PostMapping("/import")
	public ApiResponse<String> importData(@Valid @RequestBody ImportRequest importRequest) {

		try {
			importService.importFile(importRequest.getFilename());
			return ApiResponse.success(HttpStatus.OK.value(), "Import process complete");

		} catch (IOException ioe) {
			ApiError error = ApiError.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Import failed", ioe.getMessage());
			return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), error);
		}
	}
}
