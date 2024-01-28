package com.tus.cipher.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.service.ImportService;

@RestController
@RequestMapping("/sysadmin")
public class SysAdminController {

	@Autowired
	ImportService importService;

	@PostMapping("/import")
	public ResponseEntity<String> importData() throws IOException {
		System.out.println("SysAdminController::importData");

		importService.importFile("TUSGroupProject_SampleDataset.xls");
		return ResponseEntity.ok("Import process completed");
	}
}
