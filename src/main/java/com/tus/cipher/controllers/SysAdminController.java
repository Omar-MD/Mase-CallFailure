package com.tus.cipher.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.exceptions.ImportException;
import com.tus.cipher.services.ImportService;

@RestController
public class SysAdminController {

	@Autowired
	private ImportService importService;

	@PostMapping("/import")
	public ResponseEntity<?> importData() {
		importService.readExcel(null);
		throw new ImportException("Failed import");
	}
}
