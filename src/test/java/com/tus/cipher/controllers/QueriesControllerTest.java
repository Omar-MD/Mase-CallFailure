package com.tus.cipher.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
}
