package com.tus.cipher.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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

	private static final String DATA_PATH = "call-failure-data/";
	private static final String DATA_FILE = "TUS_CallFailureData";
	private static final String AUTO_SUCCESS = "AUTO_SUCCESS";
	private static final String AUTO_FAIL = "AUTO_FAIL";
	private static final String NO_AUTO = "NO_AUTO";

	private static final LoggerService logger = LoggerService.INSTANCE;
	private final ImportService importService;

	private String status;
	private String lastImportSuccess;
	private String summary;

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

	String importFile(String filename) throws IOException {
		if (filename == null || !filename.contains(DATA_FILE)) {
			throw new IOException("Invalid file selection!");
		}

		File file = new File(DATA_PATH + filename);
		if (!file.exists()) {
			throw new IOException("File does not exist: " + filename);
		}

		try (HSSFWorkbook workbook = (HSSFWorkbook) WorkbookFactory.create(file)) {
			// Log path
			String logFilePath = "logs/" + filename.substring(0, filename.lastIndexOf('.')) + "_import_log.txt";
			logger.setLogFilePath(logFilePath);
			logger.resetLogFile();

			// Execute Import
			importService.importWorkBook(workbook);
			return ErrorCountService.displaySummaryFromLog(logFilePath);
		}
	}

	public void autoImport(String filename) {
		logger.setLogFilePath("logs/auto_import_log.txt");

		try {
			summary = importFile(filename);
			logger.logMsg(summary);
			updateLastImportStatus(AUTO_SUCCESS, "Automatic import triggered: \n"+summary);

		} catch (Exception e) {
			logger.logMsg(e.getMessage());
			updateLastImportStatus(AUTO_FAIL, "Automatic import Failed: \n" + e.getMessage());
		}
	}

	@GetMapping("/auto-import-status")
	public ApiResponse<String> checkImportStatus() {

		status = (status == null)? NO_AUTO: status;
		lastImportSuccess = (lastImportSuccess == null) ? "N/A": lastImportSuccess;
		summary = (summary ==null)? "No automatic import triggered": summary;

		StringBuilder htmlContentBuilder = new StringBuilder("<h4>Automatic Import</h4>");
		htmlContentBuilder.append("<div class=\"alert alert-info\" role=\"alert\">")
		.append("<strong>Status:</strong> ").append(status).append("<br/>")
		.append("<strong>Last Import:</strong> ").append(lastImportSuccess).append("<br/>")
		.append(summary).append("</div>");

		return ApiResponse.success(determineStatusCode(status), htmlContentBuilder.toString());
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

	private void updateLastImportStatus(String s, String sum) {
		status = s;
		lastImportSuccess = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		summary = sum;
	}
}
