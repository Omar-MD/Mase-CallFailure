package com.tus.cipher.services;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class FileUtil {

	private FileUtil() {}

	public static String prepareFiles(String currentDirectory, String destinationDirectory, List<String> filesToMove, List<String> excludeFiles) {
        try {
        	handleExcludedFilesInDestination(destinationDirectory, currentDirectory, excludeFiles);
            moveSpecifiedFiles(currentDirectory, destinationDirectory, filesToMove, excludeFiles);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    private static void handleExcludedFilesInDestination(String destinationDirectory, String currentDirectory, List<String> excludeFiles) throws IOException {
        for (String fileName : excludeFiles) {
            Path filePathInDestination = Paths.get(destinationDirectory, fileName);
            if (Files.exists(filePathInDestination)) {
                Files.move(filePathInDestination, Paths.get(currentDirectory, fileName), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private static void moveSpecifiedFiles(String currentDirectory, String destinationDirectory, List<String> filesToMove, List<String> excludeFiles) throws IOException {
        try (Stream<Path> paths = Files.list(Paths.get(currentDirectory))) {
            paths.parallel()
            .filter(Files::isRegularFile)
                 .filter(path -> filesToMove.contains(path.getFileName().toString()) && !excludeFiles.contains(path.getFileName().toString()))
                 .forEach(path -> {
                     try {
                         Path destinationPath = Paths.get(destinationDirectory, path.getFileName().toString());
                         Files.move(path, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                     } catch (IOException e) {
                         throw new UncheckedIOException(e);
                     }
                 });
        }
    }

	public static String moveFileToDest(String filePath, String destinationDirectory) {
		try {
			Path sourcePath = Paths.get(filePath);

			if (!Files.exists(sourcePath)) {
				System.out.println("File does not exist: " + filePath);
				return "file_not_found";
			}

			Path destinationPath = Paths.get(destinationDirectory, sourcePath.getFileName().toString());
			Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
	}

	public static String getDate(int year, int month, int day) {
		LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);
		String pattern = "yyyy-MM-dd'T'HH:mm";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return dateTime.format(formatter);
	}
}