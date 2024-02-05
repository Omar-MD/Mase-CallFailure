package com.tus.cipher.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ErrorCountService {

	private ErrorCountService() {}

	public static int countErrors(String logFilePath) throws IOException {
		Path path = Paths.get(logFilePath);
		long errorCount = 0;
		try {
			if (!Files.exists(path)) {
				return 0;
			}
			Stream<String> lines = Files.lines(path);
			errorCount = lines.filter(line -> line.contains("ErroneusRecord")).count();
			lines.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new IOException(ioe);
		}
		return (int) errorCount;
	}

	public static String displaySummary(int errorCount) {
		StringBuilder summary = new StringBuilder();
		summary.append("<strong>Import Summary</strong>");
		summary.append("<br/>Total Count of Errors: ").append(errorCount);

		if (errorCount > 0) {
			summary.append("<br/>Please review the log file for details.");
		} else {
			summary.append("<br/>Import successful. No errors found.");
		}

		return summary.toString();
	}
}
