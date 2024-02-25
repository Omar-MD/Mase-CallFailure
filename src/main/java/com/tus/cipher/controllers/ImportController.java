package com.tus.cipher.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dto.ImportRequest;
import com.tus.cipher.responses.ApiError;
import com.tus.cipher.responses.ApiResponse;
import com.tus.cipher.services.ErrorCountService;
import com.tus.cipher.services.ImportService;
import com.tus.cipher.services.LoggerService;

@RestController
@RequestMapping("/sysadmin")
public class ImportController {

	static final String ROOT_PATH = "call-failure-data/";
	static final String LOG_FOLDER_PATH = "logs";
	static final String LOG_FILE_PATH = "logs/import_log.txt";

	static final String AUTO_SUCCESS = "Automatic import triggered Successfully!";
	static final String AUTO_FAIL = "Automatic import Failed!";
	static final String NO_AUTO = "No automatic import triggered!";

	String importStatus = NO_AUTO;
	String lastSuccessImport = null;
	String summary = null;

	ImportService importService;

	ImportController(){}

	@Autowired
	public ImportController(ImportService importService) {
		this.importService = importService;
	}

	@PostMapping("/import")
	public ApiResponse<String> importData(@Valid @RequestBody ImportRequest importRequest) throws IOException {

		try {
			String importSummary = importFile(importRequest.getFilename());
			return ApiResponse.success(HttpStatus.OK.value(), importSummary);

		} catch (IOException e) {

			ApiError error = ApiError.of("Import failed", e.getMessage());
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		}
	}

	@GetMapping("/auto-import-status")
	public ApiResponse<String> checkImportStatus() {
		String htmlContent = "<h4>Automatic Import</h4>";
		htmlContent += "<div class=\"alert alert-info\" role=\"alert\">";
		htmlContent += "<strong>Status:</strong> " + importStatus + "<br/>";
		if (lastSuccessImport != null) {
			htmlContent += "<strong>Last Import:</strong> " + lastSuccessImport + "<br/>";
		}
		if (summary != null) {
			htmlContent += summary;
		}
		htmlContent += "</div>";

		int statusCode = determineStatusCode(importStatus);
		return ApiResponse.success(statusCode, htmlContent);
	}

	private int determineStatusCode(String importStatus) {
	    switch (importStatus) {
	        case NO_AUTO:
	            return HttpStatus.NO_CONTENT.value();
	        case AUTO_SUCCESS:
	            return HttpStatus.OK.value();
	        case AUTO_FAIL:
	            return HttpStatus.BAD_REQUEST.value();
	        default:
	            return HttpStatus.INTERNAL_SERVER_ERROR.value();
	    }
	}

	public void autoImport(String filename) {
		try {
			summary = importFile(filename);
			importStatus = AUTO_SUCCESS;
			lastSuccessImport = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		} catch (Exception e) {
			importStatus = AUTO_FAIL;
			summary = e.getMessage();
		}
	}

	String importFile(String filename) throws IOException {
		// Prepare Workbook
		HSSFWorkbook workbook = createWorkbook(filename);

		// Setup Logger
		LoggerService.setLogFolderPath(LOG_FOLDER_PATH);
		LoggerService.setLogFilePath(LOG_FILE_PATH);
		LoggerService.resetLogFile();

		// Execute Import
		importService.importWorkBook(workbook);

		// Prepare Summary
		return ErrorCountService.displaySummaryFromLog(LOG_FILE_PATH);
	}

	HSSFWorkbook createWorkbook(String filename) throws IOException {
		if (filename == null || !filename.contains("TUS_CallFailureData")) {
			throw new IOException("Invalid file selection!");
		}
		return (HSSFWorkbook) WorkbookFactory.create(new File(ROOT_PATH + filename));
	}
}
