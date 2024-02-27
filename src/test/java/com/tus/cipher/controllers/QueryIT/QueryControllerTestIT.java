package com.tus.cipher.controllers.QueryIT;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.intuit.karate.junit5.Karate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QueryControllerTestIT {

	@LocalServerPort
	int randomServerPort;

    @Karate.Test
    Karate runPrepQuery() {
        System.setProperty("local.server.port", String.valueOf(randomServerPort));
        return Karate.run("classpath:com/tus/cipher/controllers/QueryIT/prep_query.feature").relativeTo(getClass());
    }

//	@Karate.Test
//	Karate runAutoImport() {
//		System.setProperty("local.server.port", String.valueOf(randomServerPort));
//		return Karate.run("classpath:com/tus/cipher/controllers/importIT/auto_import.feature").relativeTo(getClass());
//	}
}
