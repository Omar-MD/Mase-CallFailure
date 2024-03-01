package com.tus.cipher.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public enum LoggerService { // NOSONAR

    INSTANCE;

    private static final String LOG_FOLDER_PATH = "logs";
    private String logFilePath;

    private void writeToLog(String errorMessage) {
        try {
            checkLogPath();
            Path path = Paths.get(logFilePath);
            Files.write(path, (errorMessage + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logInfo(String errPath, String errDesc, String data, int rowIndex) {
        writeToLog(getTimeStamp() + " INFO path " + errPath + " " + errDesc + " ErroneusRecord: "+ data + " Row Index: " + rowIndex);
    }

    public void logMsg(String msg) {
        writeToLog(getTimeStamp() + " INFO" + " msg: " + msg);
    }

    void checkLogPath() throws IOException {
        if (logFilePath == null) {
            throw new IllegalStateException("Log file path is not set.");
        }

        // Folder
        Path folderPath = Paths.get(LOG_FOLDER_PATH);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        // File
        Path filePath = Paths.get(logFilePath);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    String getTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public void createFolder(String folderPath) throws IOException {
        Path path = Paths.get(folderPath);
        Files.createDirectories(path);
    }

    public void createFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.createFile(path);
    }

    public void resetLogFile() throws IOException {
        if (logFilePath == null) {
            throw new IllegalStateException("Log file path is not set.");
        }

        Path filePath = Paths.get(logFilePath);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
        Files.createFile(filePath);
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) { //NOSONAR
        this.logFilePath = logFilePath;
    }
}
