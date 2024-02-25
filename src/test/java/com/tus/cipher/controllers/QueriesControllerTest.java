package com.tus.cipher.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.responses.ApiResponse;

class QueriesControllerTest {

	private QueriesController queriesController;
	private CallFailureDAO callFailureDAOMock;

	@BeforeEach
	void setUp() {
		callFailureDAOMock = mock(CallFailureDAO.class);
		queriesController = new QueriesController(callFailureDAOMock);
	}

	@Test
	void testGetImsiFailures() {
		List<Long> imsiList = Arrays.asList(123456L, 789012L);
		when(callFailureDAOMock.listImsi()).thenReturn(imsiList);

		ApiResponse<Object> response = queriesController.getImsiFailures();

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.getData() instanceof List);
		assertEquals(imsiList, response.getData());
	}

	@Test
	void testFindImsiFailuresValidImsi() {
		long imsi = 123456L;
		List<Object[]> imsiEventCauseDescriptions = new ArrayList<>();
		imsiEventCauseDescriptions.add(new Object[] { 1, 100, "Description 1" });
		imsiEventCauseDescriptions.add(new Object[] { 2, 200, "Description 2" });

		when(callFailureDAOMock.listImsi()).thenReturn(Arrays.asList(imsi));
		when(callFailureDAOMock.findImsiEventCauseDescriptions(imsi)).thenReturn(imsiEventCauseDescriptions);

		ApiResponse<Object> response = queriesController.findImsiFailures(imsi);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.getData() instanceof List);
		List<?> responseData = (List<?>) response.getData();
		assertEquals(imsiEventCauseDescriptions.size(), responseData.size());
	}

	@Test
	void testFindImsiFailuresInvalidImsi() {
		long imsi = 987654L;

		when(callFailureDAOMock.listImsi()).thenReturn(Arrays.asList(123456L, 789012L));

		ApiResponse<Object> response = queriesController.findImsiFailures(imsi);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		assertTrue(response.getError() != null && response.getError().getErrorMsg().equals("Invalid Imsi"));
	}

	@Test
	void testGetModels() {
		List<Long> validTacList = Arrays.asList(123456L, 789012L);
		when(callFailureDAOMock.listTac()).thenReturn(validTacList);

		ApiResponse<Object> response = queriesController.getModelsWithFailure();

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.getData() instanceof List);
		assertEquals(validTacList, response.getData());
	}

	@Test
	void testFindModelsFailureTypesWithCountValidTac() {
		long tac = 123456L;
		List<Long> validTacList = Arrays.asList(123456L, 789012L);
		List<Object[]> modelsFailureTypesWithCount = new ArrayList<>();
		modelsFailureTypesWithCount.add(new Object[] { 1, 100, 10 });
		modelsFailureTypesWithCount.add(new Object[] { 2, 200, 5 });

		when(callFailureDAOMock.listTac()).thenReturn(validTacList);
		when(callFailureDAOMock.findModelsFailureTypesWithCount(tac)).thenReturn(modelsFailureTypesWithCount);

		ApiResponse<Object> response = queriesController.findModelsFailureTypesWithCount(tac);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.getData() instanceof List);
		List<?> responseData = (List<?>) response.getData();
		assertEquals(modelsFailureTypesWithCount.size(), responseData.size());
	}

	@Test
	void testFindModelsFailureTypesWithCountInvalidTac() {
		long tac = 987654L;
		List<Long> validTacList = Arrays.asList(123456L, 789012L);

		when(callFailureDAOMock.listTac()).thenReturn(validTacList);

		ApiResponse<Object> response = queriesController.findModelsFailureTypesWithCount(tac);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		assertTrue(response.getError() != null && response.getError().getErrorMsg().equals("Invalid Tac"));
	}

	@Test
	void testGetImsiFailuresWithTime() {
		List<Long> callFaillureImsiList = Arrays.asList(new Long(0), new Long(1));

		LocalDateTime start = LocalDateTime.of(2022, 2, 2, 2, 2, 2);
		LocalDateTime end = LocalDateTime.of(2033, 3, 3, 3, 3, 3);
		when(callFailureDAOMock.findDistinctImsiByDateTimeBetween(start, end)).thenReturn(callFaillureImsiList);

		ApiResponse<Object> response = queriesController.findImsiFailures(start, end);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.getData() instanceof List);
		assertEquals(callFaillureImsiList, response.getData());
	}

	
	@Test
	void testGetModelFailureCount() {
	    long tac = 12345L; 
	    LocalDateTime start = LocalDateTime.of(2022, 2, 2, 2, 2, 2);
	    LocalDateTime end = LocalDateTime.of(2033, 3, 3, 3, 3, 3);
	    long expectedCount = 10L; 
	    when(callFailureDAOMock.getModelFaliureCount(start, end, tac)).thenReturn(expectedCount);
	    
	    ApiResponse<Long> response = queriesController.getModelsFaliureCount(start, end, tac);

	    assertEquals("Success", response.getStatus());
	    assertEquals(expectedCount, (long) response.getData()); // Cast to long to match expected type
	}

	

}
