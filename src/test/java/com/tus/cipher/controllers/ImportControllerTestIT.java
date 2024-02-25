package com.tus.cipher.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.intuit.karate.junit5.Karate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImportControllerTestIT {

	@LocalServerPort
    int randomServerPort;

    @Karate.Test
    Karate runManualImport() {
        System.setProperty("local.server.port", String.valueOf(randomServerPort));
        return Karate.run("classpath:com/tus/cipher/controllers/manual_import.feature").relativeTo(getClass());
    }

    @Karate.Test
    Karate runAutoImport() {
        System.setProperty("local.server.port", String.valueOf(randomServerPort));
        return Karate.run("classpath:com/tus/cipher/controllers/auto_import.feature").relativeTo(getClass());
    }
}


