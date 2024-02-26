package com.tus.cipher;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.tus.cipher.dto.ImportRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


// @Disabled
@SpringBootTest
@AutoConfigureMockMvc
class MaseProjectApplicationTests {

	@Autowired
    private MockMvc mockMvc;

	// Functional test to ensure import takes less than 2 minutes to complete for 30,000 rows
	@Test
    public void testImportExecutionTime() {
        // Set a timeout of 2 minutes in milliseconds
        final long timeoutMillis = 2 * 60 * 1000;

		// importController = new ImportController(importServiceMock);

        // Record the start time
        long startTime = System.currentTimeMillis();

        try {

			ImportRequest request = new ImportRequest();
			request.setFilename("TUS_CallFailureData3A.xls");

			mockMvc.perform(post("/sysadmin/import")
                    .content("{\"filename\":\"TUS_CallFailureData3A.xls\"}")
                    // .content()
                    .contentType("application/json"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                    .andExpect(jsonPath("$.status").value("Success"));

            // Record the end time after the function execution
            final long endTime = System.currentTimeMillis();

            // Calculate the elapsed time
            final long elapsedTime = endTime - startTime;

            // Check if the elapsed time exceeds the timeout
            if (elapsedTime > timeoutMillis) {
                fail("Function execution took longer than 2 minutes");
            }
        } catch (Exception e) {
            // Handle any exceptions thrown by the function
            fail("Function threw an exception: " + e.getMessage());
        }
    }

}