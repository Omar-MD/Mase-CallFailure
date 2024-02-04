package com.tus.cipher.services.sheets;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.EventCauseDAO;
import com.tus.cipher.dto.sheets.EventCause;

@Component
public class EventCauseSheet extends BaseSheetProcessor{

	private static final String SHEET_NAME = "Event-Cause Table";
	private static final int MAX_BATCH_SIZE = 128;

	private final EventCauseDAO eventCauseDAO;
	private List<EventCause> validRows = new ArrayList<>();

	public EventCauseSheet(EventCauseDAO eventCauseDAO) {
		this.eventCauseDAO = eventCauseDAO;
	}

	@Override
	public void processRow(Row r) {

		try {
			Integer causeCode = (int) r.getCell(0).getNumericCellValue();
			Integer eventId = (int) r.getCell(1).getNumericCellValue();
			String description = r.getCell(2).getStringCellValue();

			EventCause eventCause = new EventCause(causeCode, eventId, description);
			validRows.add(eventCause);

		} catch(Exception e) {
			//TODO: Log Error
		}
	}

	@Override
	public void saveInBatchs() {
		int totalRows = validRows.size();
		for (int i = 0; i < totalRows; i += MAX_BATCH_SIZE) {
			List<EventCause> batchEntities = validRows.subList(i, Math.min(i + MAX_BATCH_SIZE, totalRows));
			eventCauseDAO.saveAllAndFlush(batchEntities);
		}
		validRows.clear();
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}
}
