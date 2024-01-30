package com.tus.cipher.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dto.ImportRequest;
import com.tus.cipher.services.ImportService;

@RestController
@RequestMapping("/sysadmin")
public class SysAdminController {

	@Autowired
	ImportService importService;

	//TODO: Clean up & improve error thrown
	@PostMapping("/import")
	public ResponseEntity<String> importData(@Valid @RequestBody ImportRequest importRequest) throws IOException {

		importService.importFile(importRequest.getFilename());
		return ResponseEntity.ok("Import process completed");
	}
}
