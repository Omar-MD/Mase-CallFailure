package com.tus.cipher.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tus.cipher.dao.EventCauseDAO;
import com.tus.cipher.dao.FailureClassDAO;
import com.tus.cipher.dao.MccMncDAO;
import com.tus.cipher.dao.UeDAO;

public class ValidationService {

	EventCauseDAO eventCauseDAO;
	MccMncDAO mccMncDAO;
	FailureClassDAO failureClassDAO;
	UeDAO ueDAO;

	Map<Integer, Set<Integer>> validEventCauseMap;
	Map<Integer, Set<Integer>> validMccMncMap;
	Set<Integer> validFailureCodeSet;
	Set<Long> validUeSet;

	public ValidationService(EventCauseDAO eventCauseDAO, MccMncDAO mccMncDAO, FailureClassDAO failureClassDAO,
			UeDAO ueDAO) {
		this.eventCauseDAO = eventCauseDAO;
		this.validEventCauseMap = createEventCauseMap(this.eventCauseDAO.findDistinctEventIdsAndCauseCodes());
		this.mccMncDAO = mccMncDAO;
		this.validMccMncMap = createMccMncMap(this.mccMncDAO.findDistinctMccAndMnc());
		this.failureClassDAO = failureClassDAO;
		this.validFailureCodeSet = new HashSet<>(this.failureClassDAO.findDistinctFailureCodes());
		this.ueDAO = ueDAO;
		this.validUeSet = new HashSet<>(this.ueDAO.findDistinctUe());
	}

	private Map<Integer, Set<Integer>> createEventCauseMap(List<Object[]> eventCauseList) {
		Map<Integer, Set<Integer>> map = new HashMap<>();
		for (Object[] ec : eventCauseList) {
			int eventId = (int) ec[0];
			int causeCode = (int) ec[1];
			map.computeIfAbsent(eventId, k -> new HashSet<>()).add(causeCode);
		}
		return map;
	}

	private Map<Integer, Set<Integer>> createMccMncMap(List<Object[]> mccMncList) {
		Map<Integer, Set<Integer>> map = new HashMap<>();
		for (Object[] mccMnc : mccMncList) {
			int mcc = (int) mccMnc[0];
			int mnc = (int) mccMnc[1];
			map.computeIfAbsent(mcc, k -> new HashSet<>()).add(mnc);
		}
		return map;
	}

	public void validate(LocalDateTime dateTime, int eventId, int causeCode, int failureCode, int duration, int cellId, // NOSONAR
			long tac, int mcc, int mnc, String neVersion, long imsi, long hier3Id, long hier32Id, long hier321Id) {

		validateNotNull(dateTime, neVersion);

		if (!isValidDateTime(dateTime)) {
			throw new IllegalArgumentException("Invalid Date Time format");
		}

		if (!isValidEventCause(eventId, causeCode)) {
			throw new IllegalArgumentException("Invalid Event Id/Cause Code combination");
		}

		if (!isValidMccMnc(mcc, mnc)) {
			throw new IllegalArgumentException("Invalid MCC/MNC combination");
		}

		if (!isValidFailureCode(failureCode)) {
			throw new IllegalArgumentException("Invalid Failure Class");
		}

		if (!isValidUeType(tac)) {
			throw new IllegalArgumentException("Invalid Ue Type");
		}

		if (!isValidIMSI(String.valueOf(imsi))) {
			throw new IllegalArgumentException("Invalid IMSI format");
		}

		if (duration <= 0 || cellId <= 0 || hier3Id <= 0 || hier32Id <= 0 || hier321Id <= 0) {
			throw new IllegalArgumentException(
					"Invalid Duration/CellId/Hier3/Hier32/Hier321: Parameters must be positive");
		}
	}

	private void validateNotNull(Object... objects) {
		for (Object obj : objects) {
			if (obj == null) {
				throw new IllegalArgumentException("Invalid Date/NE Version: Parameter cannot be null");
			}
		}
	}

	private boolean isValidDateTime(LocalDateTime dateTime) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			String formattedDateTime = dateTime.format(formatter);
			return formattedDateTime.equals(dateTime.toString());

		} catch (DateTimeParseException e) {
			return false;
		}
	}

	private boolean isValidEventCause(int eventId, int causeCode) {
		Set<Integer> causeCodes = validEventCauseMap.get(eventId);
		return causeCodes != null && causeCodes.contains(causeCode);
	}

	private boolean isValidMccMnc(int mcc, int mnc) {
		Set<Integer> mncs = validMccMncMap.get(mcc);
		return mncs != null && mncs.contains(mnc);
	}

	private boolean isValidFailureCode(int failureCode) {
		return validFailureCodeSet.contains(failureCode);
	}

	private boolean isValidUeType(Long tac) {
		return validUeSet.contains(tac);
	}

	private boolean isValidIMSI(String imsi) {
		int length = imsi.length();
		return length == 14 || length == 15;
	}
}
