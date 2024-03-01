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
    private static final String TEST_LOG_FILE_PATH = "logs/test_log.txt";

    private LoggerService loggerService;

    @BeforeEach
    void setUp() {
        loggerService = LoggerService.INSTANCE;
        loggerService.setLogFilePath(TEST_LOG_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path testFolderPath = Paths.get(TEST_LOG_FOLDER_PATH);
        if (Files.exists(testFolderPath)) {
            Files.walk(testFolderPath).map(Path::toFile).forEach(File::delete);
        }
    }

    @Test
    void resetLogFileTest() throws IOException {
        String errorMessage = "Test error message";
        loggerService.logMsg(errorMessage);
        Path filePath = Paths.get(TEST_LOG_FILE_PATH);
        loggerService.resetLogFile();
        assertTrue(Files.exists(filePath), "Log file should exist");
        assertEquals("", Files.readString(filePath).trim(), "Log file should be empty");
    }

    @Test
    void checkLogPath_shouldCreateFolderAndFileIfNotExist() throws IOException {
        loggerService.checkLogPath();
        Path folderPath = Paths.get(TEST_LOG_FOLDER_PATH);
        Path filePath = Paths.get(TEST_LOG_FILE_PATH);
        assertTrue(Files.exists(folderPath), "Log folder should be created");
        assertTrue(Files.exists(filePath), "Log file should be created");
    }

    @Test
    void checkLogPath_shouldNotRecreateFolderAndFileIfExist() throws IOException {
        loggerService.createFolder(TEST_LOG_FOLDER_PATH); // Creating folder
        loggerService.createFile(TEST_LOG_FILE_PATH); // Creating file
        loggerService.checkLogPath();
        Path folderPath = Paths.get(TEST_LOG_FOLDER_PATH);
        Path filePath = Paths.get(TEST_LOG_FILE_PATH);
        assertTrue(Files.exists(folderPath), "Log folder should exist");
        assertTrue(Files.exists(filePath), "Log file should exist");
    }

    @Test
    void logMsg_shouldAppendMessageToFile() throws IOException {
        String errorMessage = "Test error message";
        loggerService.logMsg(errorMessage);
        Path filePath = Paths.get(TEST_LOG_FILE_PATH);
        assertTrue(Files.exists(filePath), "Log file should be created");
        String expectedLog = String.format("%s INFO msg: %s", loggerService.getTimeStamp(), errorMessage);
        String actualLog = Files.readString(filePath).trim();
        assertEquals(expectedLog, actualLog, "Log file content should match"); }

    @Test
    void logInfo_shouldLogInfoDetailsToFile() throws IOException {
        String data = "Test data";
        String errDesc = "Test error description";
        int rowIndex = 123;

        loggerService.logInfo(data, errDesc, data, rowIndex);
        Path filePath = Paths.get(TEST_LOG_FILE_PATH);
        assertTrue(Files.exists(filePath), "Log file should be created");

        String expectedLog = String.format("%s INFO path %s %s ErroneusRecord: %s Row Index: %d",
                                            loggerService.getTimeStamp(), data, errDesc, data, rowIndex);
        assertEquals(expectedLog, Files.readString(filePath).trim(), "Log file content should match");
    }
}
