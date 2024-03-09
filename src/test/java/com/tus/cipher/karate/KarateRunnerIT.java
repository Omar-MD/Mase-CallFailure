package com.tus.cipher.karate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.tus.cipher.services.FileUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KarateRunnerIT {

	@LocalServerPort
	int randomServerPort;

	@BeforeAll
	static void setup() {
		List<String> filesToMove = List.of("TUS_CallFailureData3A.xls", "TUS_CallFailureData3B.xls");
		List<String> excludedFiles = List.of("TUS_CallFailureData.xls", "wrong.xls");
		FileUtil.prepareFiles(".", "call-failure-data", filesToMove, excludedFiles);
	}

	@Test
    void executeImportFeatures() {
		System.setProperty("local.server.port", String.valueOf(randomServerPort));
        Results results = Runner.path(
        		"classpath:com/tus/cipher/karate/import/man_import.feature",
        		"classpath:com/tus/cipher/karate/import/auto_import.feature")
        .parallel(2);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }
}
