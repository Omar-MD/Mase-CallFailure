package com.tus.cipher.config;

import java.io.File;
import java.time.Duration;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.tus.cipher.controllers.ImportController;

@Component
@Configuration
public class FileWatcherConfig {

	private static final String CALL_FAILURE_DIR = "call-failure-data/";
	private FileSystemWatcher fileSystemWatcher;

	@Bean
	@Autowired
	FileSystemWatcher fileSystemWatcher(ImportController importController) {
		fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(1000L), Duration.ofMillis(500L));
		fileSystemWatcher.addSourceDirectory(new File(CALL_FAILURE_DIR));
		fileSystemWatcher.addListener(new MyFileChangeListener(importController));
		fileSystemWatcher.start();
		System.out.println("FileSystemWatcher initialized, monitoring directory: "+ CALL_FAILURE_DIR);
		return fileSystemWatcher;
	}

    @PreDestroy
    public void stopFileSystemWatcher() {
        if (fileSystemWatcher != null) {
            fileSystemWatcher.stop();
            System.out.println("Stopped fileSystemWatcher");
        }
    }
}
