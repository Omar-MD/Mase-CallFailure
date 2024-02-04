package com.tus.cipher.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ErrorCountService {

	private ErrorCountService() {
	}

	public static void countError() {
		String logFilePath = "logs\\error_log.txt";

		try {
			int errorCount = countErrors(logFilePath);
			displaySummary(errorCount);
		} catch (IOException e) {
			System.err.println("Error reading log file: " + e.getMessage());
		}
	}

	public static int countErrors(String logFilePath) throws IOException {
		Path path = Paths.get(logFilePath);
		int errorCount = 0;
		try (var lines = Files.lines(path)) {
			errorCount = (int) lines.filter(line -> line.contains("InvalidData")).count();
		}
		return errorCount;
	}

	public static String displaySummary(int errorCount) {
		StringBuilder summary = new StringBuilder();
		summary.append("\n****Import Summary****\n");
		summary.append("\nTotal Count of Errors: ").append(errorCount);

		if (errorCount > 0) {
			summary.append("\n\nPlease review the log file for details.");
		} else {
			summary.append("\nImport successful. No errors found.");
		}

		return summary.toString();
	}
}
