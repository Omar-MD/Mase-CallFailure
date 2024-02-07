package com.tus.cipher.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoggerServiceTest {

	private static final String TEST_LOG_FOLDER_PATH = "logs";
	private static final String TEST_LOG_FILE_PATH = "logs/import_log.txt";

	@BeforeEach
	void setUp() {
		LoggerService.setLogFilePath(TEST_LOG_FILE_PATH);
	}

	@AfterEach
	void tearDown() throws IOException {
		// Delete test log folder and files after each test
		Path testFolderPath = Paths.get(TEST_LOG_FOLDER_PATH);
		if (Files.exists(testFolderPath)) {
			Files.walk(testFolderPath).map(Path::toFile).forEach(File::delete);
		}
	}

	@Test
	void resetLogFileTest() throws IOException {
		String errorMessage = "Test error message";
		LoggerService.logInvalidData(errorMessage);
		Path filePath = Paths.get(TEST_LOG_FILE_PATH);
		LoggerService.resetLogFile();
		assertTrue(Files.exists(filePath), "Log file should exist");
		assertEquals("", Files.readString(filePath).trim(), "Log file should be empty");
	}

	@Test
	void checkLogPath_shouldCreateFolderAndFileIfNotExist() {
		LoggerService.checkLogPath();
		Path folderPath = Paths.get(TEST_LOG_FOLDER_PATH);
		Path filePath = Paths.get(TEST_LOG_FILE_PATH);
		assertTrue(Files.exists(folderPath), "Log folder should be created");
		assertTrue(Files.exists(filePath), "Log file should be created");
	}

	@Test
	void checkLogPath_shouldNotRecreateFolderAndFileIfExist() throws IOException {
		LoggerService.createFolder(TEST_LOG_FOLDER_PATH);
		LoggerService.createFile(TEST_LOG_FILE_PATH);
		LoggerService.checkLogPath();
		Path folderPath = Paths.get(TEST_LOG_FOLDER_PATH);
		Path filePath = Paths.get(TEST_LOG_FILE_PATH);
		assertTrue(Files.exists(folderPath), "Log folder should exist");
		assertTrue(Files.exists(filePath), "Log file should exist");
	}

	@Test
	void logInvalidData_shouldAppendErrorMessageToFile() throws IOException {
		String errorMessage = "Test error message";
		LoggerService.logInvalidData(errorMessage);
		Path filePath = Paths.get(TEST_LOG_FILE_PATH);
		assertTrue(Files.exists(filePath), "Log file should be created");
		assertEquals(errorMessage, Files.readString(filePath).trim(), "Log file content should match");
	}

	@Test
	void loggerService_shouldLogErrorDetailsToFile() throws IOException {
		String data = "Test data";
		String errDesc = "Test error description";
		String errSeverity = "ERROR";
		String errCode = "1001";

		LoggerService.log(data, errDesc, errSeverity, errCode);
		Path filePath = Paths.get(TEST_LOG_FILE_PATH);
		assertTrue(Files.exists(filePath), "Log file should be created");
		String expectedLog = String.format("%s %s %s %s ErroneusRecord: %s", LoggerService.getTimeStamp(), errSeverity,
				errCode, errDesc, data);
		assertEquals(expectedLog, Files.readString(filePath).trim(), "Log file content should match");
	}
}
