package com.tus.cipher.controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.services.ImportService;

@RestController
public class SysAdminController {

	private static final String FILE_NAME = "TUSGroupProject_SampleDataset.xls";

	@PostMapping("/import")
	public void importData() {
		try {
			ImportService.readExcel(FILE_NAME);

		} catch (IOException e) {

			throw new RuntimeException("Failed to import data: " + e.getMessage(), e);
		}

	}
}
