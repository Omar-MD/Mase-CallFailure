package com.tus.cipher.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tus.cipher.dao.EventCauseDAO;
import com.tus.cipher.dao.FailureClassDAO;
import com.tus.cipher.dao.MccMncDAO;
import com.tus.cipher.dao.UeDAO;

class DataValidatorTest {

	private EventCauseDAO eventCauseDAO;
	private MccMncDAO mccMncDAO;
	private FailureClassDAO failureClassDAO;
	private UeDAO ueDAO;

	private DataValidator validator;
	private LocalDateTime validDateTime;

	@BeforeEach
	void setUp() {
		mccMncDAO = mock(MccMncDAO.class);
		ueDAO = mock(UeDAO.class);
		failureClassDAO = mock(FailureClassDAO.class);
		eventCauseDAO = mock(EventCauseDAO.class);
		validator = new DataValidator(mccMncDAO, ueDAO, failureClassDAO, eventCauseDAO);
		validDateTime = LocalDateTime.parse("2020-01-01T12:00");
	}

	@Test
	void testIsValidDateTime() {
		assertTrue(validator.isValidDateTime(validDateTime));
		assertFalse(validator.isValidDateTime(null));
	}

	@Test
	void testIsValidIMSI() {
		assertTrue(validator.isValidIMSI("12345678901234")); // 14 digits
		assertTrue(validator.isValidIMSI("123456789012345")); // 15 digits

		assertFalse(validator.isValidIMSI("1234567890123")); // 13 digits
		assertFalse(validator.isValidIMSI("1234567890123456")); // 16 digits

		// Non-digit characters
		assertFalse(validator.isValidIMSI("1234567890123a")); // Contains a letter
	}

	@Test
	void testIsValidEventCause() {
		List<Object[]> mockEventCauseData = List.of(new Object[][] { { 1, 100 } });
		validator.setValidEventCauseMap(mockEventCauseData);

		assertTrue(validator.isValidEventCause(1, 100));
		assertFalse(validator.isValidEventCause(1, 101));
	}

	@Test
	void testIsValidMccMnc() {
		List<Object[]> mockMccMncData = List.of(new Object[][] { { 260, 2 } });
		validator.setValidMccMncMap(mockMccMncData);

		assertTrue(validator.isValidMccMnc(260, 2));
		assertFalse(validator.isValidMccMnc(999, 99));
	}

	@Test
	void testIsValidFailureCode() {
		List<Integer> mockFailureCodeData = List.of(0, 1);
		validator.setValidFailureCodeSet(mockFailureCodeData);

		assertTrue(validator.isValidFailureCode(0));
		assertFalse(validator.isValidFailureCode(2));
	}

	@Test
	void testIsValidUeType() {
		List<Long> mockUeData = List.of(100200L, 100500L);
		validator.setValidUeSet(mockUeData);

		assertTrue(validator.isValidUeType(100200L));
		assertFalse(validator.isValidUeType(111111L));
	}

	@Test
	void testValidateWithNullInputs() {
		// Test with null dateTime
		Exception dateTimeException = assertThrows(IllegalArgumentException.class, () -> validator.validate(null, 1,
				100, 0, 10, 1, 100000L, 260, 2, "NE-Version", 12345678901234L, 1L, 1L, 1L));
		assertTrue(dateTimeException.getMessage().contains("Invalid Date/NE Version: Parameter cannot be null"));

		// Test with null neVersion
		Exception neVersionException = assertThrows(IllegalArgumentException.class, () -> validator
				.validate(validDateTime, 1, 100, 0, 10, 1, 100000L, 260, 2, null, 12345678901234L, 1L, 1L, 1L));
		assertTrue(neVersionException.getMessage().contains("Invalid Date/NE Version: Parameter cannot be null"));
	}

	@Test
	void testValidateWithInvalidEventCause() {
		List<Object[]> mockEventCauseData = List.of(new Object[][] { { 1, 99 } });
		validator.setValidEventCauseMap(mockEventCauseData);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(validDateTime, 1,
				100, 0, 10, 1, 100000L, 260, 2, "NE-Version", 12345678901234L, 1L, 1L, 1L));
		assertEquals("Invalid Event Id/Cause Code combination", exception.getMessage());
	}

	@Test
	void testValidateWithInvalidMccMnc() {
		List<Object[]> mockEventCauseData = List.of(new Object[][] { { 1, 100 } });
		validator.setValidEventCauseMap(mockEventCauseData);

		List<Object[]> mockMccMncData = List.of(new Object[][] { { 260, 1 } }); // Ensure this doesn't match the test
		validator.setValidMccMncMap(mockMccMncData);																		// MCC/MNC

		Exception exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(validDateTime, 1,
				100, 0, 10, 1, 100000L, 999, 99, "NE-Version", 12345678901234L, 1L, 1L, 1L));
		assertEquals("Invalid MCC/MNC combination", exception.getMessage());
	}
}
