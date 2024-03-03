package com.tus.cipher.karate;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.intuit.karate.junit5.Karate;
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

	@Karate.Test
	Karate executeTests() {
		System.setProperty("local.server.port", String.valueOf(randomServerPort));
		return Karate.run("classpath:com/tus/cipher/karate/orchestrator.feature").relativeTo(getClass());
	}
}
