package com.tus.cipher.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ErrorCountServiceTest {

	private static final String TEST_LOG_FILE = "logs/error_log.txt";

	@AfterEach
	void tearDown() throws IOException {
		Files.deleteIfExists(Paths.get(TEST_LOG_FILE));
	}

	@Test
	void testCountErrors() throws IOException {
		String testLogContent = "This is a test log\nErroneusRecord\nAnother error\nErroneusRecord";
		createTestLogFile(TEST_LOG_FILE, testLogContent);
		int errorCount = ErrorCountService.countErrors(TEST_LOG_FILE);
		assertEquals(2, errorCount);
	}

	@Test
	void testDisplaySummaryWithErrors() {
		int errorCount = 3;
		String summary = ErrorCountService.displaySummary(errorCount);
		assertTrue(summary.contains("Total Error Count: 3"));
		assertTrue(summary.contains("Please review the log file for details."));
	}

	@Test
	void testDisplaySummaryNoErrors() {
		int errorCount = 0;
		String summary = ErrorCountService.displaySummary(errorCount);
		assertTrue(summary.contains("Total Error Count: 0"));
		assertTrue(summary.contains("Import successful. No errors found."));
	}

	private void createTestLogFile(String testLogFilePath, String testLogContent) {
		try {
			Path path = Paths.get(testLogFilePath);

			// Create directories if they don't exist
			Files.createDirectories(path.getParent());

			// Write content to the file
			Files.write(path, testLogContent.getBytes(), StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			System.err.println("Error creating test log file: " + e.getMessage());
		}
	}
}
