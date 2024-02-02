package com.tus.cipher.services.sheets;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.EventCauseDAO;
import com.tus.cipher.dto.sheets.EventCause;

@Component
public class EventCauseSheet implements SheetProcessor<EventCause> {

	private static final String SHEET_NAME = "Event-Cause Table";

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}

	@Autowired
	EventCauseDAO eventCauseDAO;

	@Override
	public EventCauseDAO getDAO() {
		return eventCauseDAO;
	}

	@Override
	public Class<EventCause> getType() {
		return EventCause.class;
	}

	@Override
	public EventCause processRow(Row r) {
		EventCause eventCause = null;

		Integer causeCode = null;
		Integer eventId = null;
		String description = null;

		try {
			causeCode = (int) r.getCell(0).getNumericCellValue();
			eventId = (int) r.getCell(1).getNumericCellValue();
			description = r.getCell(2).getStringCellValue();

		} catch (Exception e) {
			return null;
		}

		if (causeCode != null && eventId != null && description != null) {
			eventCause = new EventCause(causeCode, eventId, description);
		}

		return eventCause;
	}
}
