package com.tus.cipher.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.tus.cipher.dto.ImportRequest;
import com.tus.cipher.responses.ApiResponse;
import com.tus.cipher.services.DataValidator;
import com.tus.cipher.services.ImportParams;
import com.tus.cipher.services.ImportService;
import com.tus.cipher.services.sheets.BaseDataSheet;
import com.tus.cipher.services.sheets.BaseSheetProcessor;

class ImportControllerTest {

	private ImportController importController;
	private ImportService importServiceMock;
	private ImportParams importParamsMock;
	private DataValidator dataValidatorMock;

	@BeforeEach()
	void setup() {
		importParamsMock = mock(ImportParams.class);
		importServiceMock = mock(ImportService.class);
		dataValidatorMock = mock(DataValidator.class);

		// Configure behaviours
		when(importParamsMock.getRefProcessors()).thenReturn(List.of(mock(BaseSheetProcessor.class)));
		when(importParamsMock.getDataValidator()).thenReturn(dataValidatorMock);
		when(importParamsMock.getBaseDataSheet()).thenReturn(mock(BaseDataSheet.class));

		doNothing().when(dataValidatorMock).prepareValidator();
	}

	@Test
	void testImportSuccess() throws IOException {
		// Mock import request
		ImportRequest importRequest = new ImportRequest();
		importRequest.setFilename("TestFile.xls");

		// Create a Spy
        importController = spy(new ImportController());
        importController.importService = importServiceMock;

	    // Mock  ImportService & createWorkbook method
	    doReturn(new HSSFWorkbook()).when(importController).createWorkbook(anyString());
	    doNothing().when(importServiceMock).importWorkBook(any(HSSFWorkbook.class));

		ApiResponse<String> responseEntity = importController.importData(importRequest);

		// Verify response
		assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode());
		assertEquals("Success", responseEntity.getStatus());
		assertEquals("<strong>Import Summary</strong><br/>Total Count of Errors: 0<br/>Import successful. No errors found.", responseEntity.getData());
	}

	@Test
	void testImportFailure() throws IOException {
		ImportRequest importRequest = new ImportRequest();
		importController = new ImportController(importServiceMock);
		ApiResponse<String> responseEntity = importController.importData(importRequest);

		// Verify response
		assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode());
		assertEquals("Error", responseEntity.getStatus());
		assertEquals("Import failed", responseEntity.getError().getErrorMsg());
		assertEquals("Invalid file selection!", responseEntity.getError().getDetails());
	}
}
