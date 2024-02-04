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

	private final EventCauseDAO eventCauseDAO;
	private final MccMncDAO mccMncDAO;
	private final FailureClassDAO failureClassDAO;
	private final UeDAO ueDAO;

	private Map<Integer, Set<Integer>> validEventCauseMap;
	private Map<Integer, Set<Integer>> validMccMncMap;
	private Set<Integer> validFailureCodeSet;
	private Set<Long> validUeSet;

	public ValidationService(MccMncDAO mccMncDAO, UeDAO ueDAO, FailureClassDAO failureClassDAO,
			EventCauseDAO eventCauseDAO) {
		this.mccMncDAO = mccMncDAO;
		this.ueDAO = ueDAO;
		this.failureClassDAO = failureClassDAO;
		this.eventCauseDAO = eventCauseDAO;
	}

	public void prepareValidator() {
		this.validMccMncMap = createMccMncMap(this.mccMncDAO.findDistinctMccAndMnc());
		this.validUeSet = new HashSet<>(this.ueDAO.findDistinctUe());
		this.validFailureCodeSet = new HashSet<>(this.failureClassDAO.findDistinctFailureCodes());
		this.validEventCauseMap = createEventCauseMap(this.eventCauseDAO.findDistinctEventIdsAndCauseCodes());
	}

	public void validate(LocalDateTime dateTime, int eventId, int causeCode, int failureCode, int duration, int cellId, // NOSONAR
			long tac, int mcc, int mnc, String neVersion, long imsi, long hier3Id, long hier32Id, long hier321Id)
			throws IllegalArgumentException {

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

	private Map<Integer, Set<Integer>> createEventCauseMap(List<Object[]> eventCauseList) {
		Map<Integer, Set<Integer>> map = new HashMap<>();
		for (Object[] ec : eventCauseList) {
			int eventId = (int) ec[0];
			int causeCode = (int) ec[1];
			map.computeIfAbsent(causeCode, k -> new HashSet<>()).add(eventId);
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

	private void validateNotNull(LocalDateTime dateTime, String neVersion) {
		if (dateTime == null || neVersion == null) {
			throw new IllegalArgumentException("Invalid Date/NE Version: Parameter cannot be null");
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
		Set<Integer> eventIds = validEventCauseMap.get(causeCode);
		return eventIds != null && eventIds.contains(eventId);
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
