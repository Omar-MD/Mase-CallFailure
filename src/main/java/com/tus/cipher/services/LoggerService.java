package com.tus.cipher.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerService {

	private static String logFolderPath = "logs";
	private static String logFilePath = "logs/import_log.txt";

	private LoggerService() {
	}

	static void checkLogPath() {
		// Folder
		Path folderPath = Paths.get(logFolderPath);
		try {
			if (!Files.exists(folderPath)) {
				// Create Folder if doesn't exist
				Files.createDirectories(folderPath);
			}
		} catch (IOException e) {
			System.err.println(getTimeStamp() + "  Error checking or creating log folder ( " + folderPath + " ): "
					+ e.getMessage());
		}
		// File
		Path filePath = Paths.get(logFilePath);
		try {
			if (!Files.exists(filePath)) {
				// Create file if it doesn't exist
				Files.createFile(filePath);
			}
		} catch (IOException e) {
			System.err.println(getTimeStamp() + "  Error recreating log file ( " + filePath + " ): " + e.getMessage());
		}
	}

	static String getTimeStamp() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"));
	}

	static void logInvalidData(String errorMessage) {
		checkLogPath();
		try {

			Path path = Paths.get(logFilePath);
			Files.write(path, errorMessage.getBytes(), StandardOpenOption.APPEND);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static void log(String data, String errDesc, String errSeverity, String errCode) {
		String timeStamp = getTimeStamp();
		logInvalidData(
				"\n" + timeStamp + " " + errSeverity + " " + errCode + " " + errDesc + " ErroneusRecord: " + data);
	}

	public static void logInfo(String errPath, String errDesc, String data) {
		String timeStamp = getTimeStamp();

		logInvalidData(
				"\n" + timeStamp + " " + "INFO" + " " + "path:" + errPath + " " + errDesc + " ErroneusRecord: " + data);
	}

	public static String getLogFolderPath() {
		return logFolderPath;
	}

	public static String getLogFilePath() {
		return logFilePath;
	}

	public static void setLogFolderPath(String logFolderPath) {
		LoggerService.logFolderPath = logFolderPath;
	}

	public static void setLogFilePath(String logFilePath) {
		LoggerService.logFilePath = logFilePath;
	}

	public static void createFolder(String folderPath) throws IOException {
		Path path = Paths.get(folderPath);
		Files.createDirectories(path);
	}

	public static void createFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		Files.createFile(path);
	}

	public static void resetLogFile() throws IOException {
		Path filePath = Paths.get(logFilePath);
		if(Files.exists(filePath)) {
			Files.delete(filePath);
		}
		Files.createFile(filePath);
	}
}
