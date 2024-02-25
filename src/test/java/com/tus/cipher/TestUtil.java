package com.tus.cipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class TestUtil {

	 public static String moveFilesToDest(String currentDirectory, String destinationDirectory, String keyword) {
	        try (Stream<Path> paths = Files.list(Paths.get(currentDirectory))) {
	            paths.filter(Files::isRegularFile)
	                    .filter(path -> path.getFileName().toString().contains(keyword))
	                    .forEach(path -> {
	                        try {
	                            Path destinationPath = Paths.get(destinationDirectory, path.getFileName().toString());
	                            Files.move(path, destinationPath);
	                            System.out.println("Moved " + path.getFileName() + " to " + destinationPath);
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                    });
	            return "success";
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "error";
	        }
	 }

	 public static String moveFileToDest(String filePath, String destinationDirectory) {
		    try {
		        Path sourcePath = Paths.get(filePath);

		        // Check if the file exists before attempting to move
		        if (!Files.exists(sourcePath)) {
		            System.out.println("File does not exist: " + filePath);
		            return "file_not_found";
		        }

		        Path destinationPath = Paths.get(destinationDirectory, sourcePath.getFileName().toString());
		        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
		        System.out.println("Moved " + sourcePath.getFileName() + " to " + destinationPath);
		        return "success";
		    } catch (IOException e) {
		        e.printStackTrace();
		        return "error";
		    }
		}
}
