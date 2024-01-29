package com.tus.cipher.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tus.cipher.dao.EventCauseDAO;
import com.tus.cipher.dto.EventCause;

@Component
public class ImportEventCause implements SheetProcessor {

	private static final String SHEET_NAME = "Event-Cause Table";
	private List<EventCause> validRecords = new ArrayList<>();

	@Autowired
	EventCauseDAO eventCauseDAO;

	@Transactional
	@Override
	public void processSheet(HSSFSheet sheet) {

		int totalRows = sheet.getPhysicalNumberOfRows();
		System.out.println("=>" + sheet.getSheetName() + "\t\trow(s): " + totalRows);

		// Skip Header Row
		for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
			Row r = sheet.getRow(rowIndex);
			if (r != null) {
				processRow(r);
			}
		}

		eventCauseDAO.saveAllAndFlush(validRecords);
	}

	private void processRow(Row r) { // NOSONAR

		boolean isValidRow = true;
		int causeCode = 0;
		int eventID = 0;
		String description = null;

		for (Cell c : r) {
			if (c != null) {
				int index = c.getColumnIndex();

				switch (c.getCellType()) {
				case NUMERIC:
					if (index == 0) {
						causeCode = (int) c.getNumericCellValue();

					} else if (index == 1) {
						eventID = (int) c.getNumericCellValue();
					}
					break;

				case STRING:
					if (index == 2) {
						description = c.getStringCellValue();
					}
					break;

				default:
					isValidRow = false;
				}

			} else {
				isValidRow = false;
				break;
			}
		}

		if (isValidRow) {
			validRecords.add(new EventCause(causeCode, eventID, description));
		}
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}
}
