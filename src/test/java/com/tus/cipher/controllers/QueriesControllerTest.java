package com.tus.cipher.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.dao.FailureClassDAO;
import com.tus.cipher.dto.sheets.FailureClass;
import com.tus.cipher.responses.ApiResponse;

class QueriesControllerTest {

	private QueriesController queriesController;
	private CallFailureDAO callFailureDAOMock;
	private FailureClassDAO failureClassDAOMock;

	@BeforeEach
	void setUp() {
		callFailureDAOMock = mock(CallFailureDAO.class);
		failureClassDAOMock = mock(FailureClassDAO.class);
		queriesController = new QueriesController(callFailureDAOMock, failureClassDAOMock);
	}

	@Test
	void testGetImsiFailures() {
		List<Long> imsiList = Arrays.asList(123456L, 789012L);
		when(callFailureDAOMock.listImsi()).thenReturn(imsiList);

		ApiResponse<List<Long>> response = queriesController.getImsiFailures();

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

		ApiResponse<List<Long>> response = queriesController.getModelsWithFailure();

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.getData() instanceof List);
		assertEquals(validTacList, response.getData());
	}

	@Test
	void testGetFailureClasses() {
		List<FailureClass> failureClasses = Arrays.asList(new FailureClass(), new FailureClass());
		when(failureClassDAOMock.findAll()).thenReturn(failureClasses);

		ApiResponse<List<FailureClass>> response = queriesController.getIMSIFailureForCauseClass();

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.getData() instanceof List);
		assertEquals(failureClasses, response.getData());
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
		List<Long> callFaillureImsiList = Arrays.asList(Long.valueOf(0), Long.valueOf(1));

		LocalDateTime start = LocalDateTime.of(2022, 2, 2, 2, 2, 2);
		LocalDateTime end = LocalDateTime.of(2033, 3, 3, 3, 3, 3);
		when(callFailureDAOMock.findDistinctImsiByDateTimeBetween(start, end)).thenReturn(callFaillureImsiList);

		ApiResponse<Object> response = queriesController.findImsiFailures(start, end);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.getData() instanceof List);
		assertEquals(callFaillureImsiList, response.getData());
	}

	@Test
	void testGetImsiFailuresWithTimeError() {

		LocalDateTime end = LocalDateTime.of(2022, 2, 2, 2, 2, 2);
		LocalDateTime start = LocalDateTime.of(2033, 3, 3, 3, 3, 3);

		ApiResponse<Object> response = queriesController.findImsiFailures(start, end);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		assertEquals(null, response.getData());
		assertEquals("End date must be after start date", response.getError().getDetails());
		assertEquals("Bad Date Range", response.getError().getErrorMsg());
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
		assertEquals(expectedCount, (long) response.getData());
	}

	@Test
	void testGetModelFailureCountError() {
		long tac = 12345L;
		LocalDateTime end = LocalDateTime.of(2022, 2, 2, 2, 2, 2);
		LocalDateTime start = LocalDateTime.of(2033, 3, 3, 3, 3, 3);

		ApiResponse<Long> response = queriesController.getModelsFaliureCount(start, end, tac);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		assertEquals(null, response.getData());
		assertEquals("End date must be after start date", response.getError().getDetails());
		assertEquals("Bad Date Range", response.getError().getErrorMsg());
	}

	@Test
	void testGetIMSIFailureClasses() {
		List<Long> imsisList = Arrays.asList(1000L, 2000L, 3000L, 4000L, 5000L);
		when(callFailureDAOMock.getIMSIsWithFailureClass(1L)).thenReturn(imsisList);
		ApiResponse<List<Long>> response = queriesController.getIMSIFailureClasses(1L);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.getData() instanceof List<?>);
		assertEquals(response.getData(), imsisList);
	}

	@Test
	void testGetImsiFailureCountWithTime() {

		Long imsi = Long.valueOf(12345678);
		LocalDateTime start = LocalDateTime.of(2022, 2, 2, 2, 2, 2);
		LocalDateTime end = LocalDateTime.of(2033, 3, 3, 3, 3, 3);

		Long resultCount = Long.valueOf(4321);

		when(callFailureDAOMock.countByImsiAndDateTimeBetween(imsi, start, end)).thenReturn(resultCount);

		ApiResponse<Long> response = queriesController.getImsiFailureCountTimeRange(imsi, start, end);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertTrue(response.getData() instanceof Long);
		assertEquals(response.getData(), resultCount);
	}

	@Test
	void testGetImsiFailureCountWithTimeFailure() {

		Long imsi = Long.valueOf(12345678);
		LocalDateTime end = LocalDateTime.of(2022, 2, 2, 2, 2, 2);
		LocalDateTime start = LocalDateTime.of(2033, 3, 3, 3, 3, 3);

		Long resultCount = Long.valueOf(4321);

		when(callFailureDAOMock.countByImsiAndDateTimeBetween(imsi, start, end)).thenReturn(resultCount);

		ApiResponse<Long> response = queriesController.getImsiFailureCountTimeRange(imsi, start, end);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		assertEquals("Bad Date Range", response.getError().getErrorMsg());
		assertEquals("End date must be after start date", response.getError().getDetails());
	}

	@Test
	void testGetCallFailuresWithCountAndDuration() {
		// Mock data
		LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2024, 1, 31, 23, 59);

		List<Object[]> expectedResult = new ArrayList<>();
		expectedResult.add(new Object[] { "IMSI_1", 10L, 3600L });
		expectedResult.add(new Object[] { "IMSI_2", 5L, 1800L });

		when(callFailureDAOMock.findAllImsiFailureCountAndDuration(startDate, endDate)).thenReturn(expectedResult);

		// Call the method from your service class that uses the repository method
		List<Object[]> actualResult = callFailureDAOMock.findAllImsiFailureCountAndDuration(startDate, endDate);

		// Assert the result
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testGetTop10ImsiFailuresValidDate() {
		LocalDateTime startDate = LocalDateTime.of(2019, 4, 4, 6, 4, 2);
		LocalDateTime endDate = LocalDateTime.of(2024, 1, 2, 3, 4, 5);
		List<Object[]> testListTop10ImsiFailures = new ArrayList<>();
		when(callFailureDAOMock.findTop10IMSIWithFailures(startDate, endDate)).thenReturn(testListTop10ImsiFailures);
		ApiResponse<List<Map<String, Object>>> response = queriesController.getTop10ImsiFailures(startDate, endDate);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertEquals("Success", response.getStatus());
	}

	@Test

	void testGetTop10ImsiFailuresInValidDate() {
		LocalDateTime endDate = LocalDateTime.of(2015, 5, 5, 5, 5, 5);
		LocalDateTime startDate = LocalDateTime.of(2024, 4, 4, 4, 4, 4);
		ApiResponse<List<Map<String, Object>>> response = queriesController.getTop10ImsiFailures(startDate, endDate);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		assertEquals(null, response.getData());
		assertEquals("End date must be after start date", response.getError().getDetails());
		assertEquals("Bad Date Range", response.getError().getErrorMsg());
	}

}
