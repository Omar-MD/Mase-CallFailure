package com.tus.cipher.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.dto.CallFailure;
import com.tus.cipher.dto.CellDetail;
import com.tus.cipher.dto.FailureDetail;
import com.tus.cipher.dto.SubscriberDetail;

@Component
public class ImportBaseData implements SheetProcessor {

	private static final String SHEET_NAME = "Base Data";
	private List<CallFailure> validRecords = new ArrayList<>();

	@Autowired
	CallFailureDAO callFailureDAO;

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

		callFailureDAO.saveAllAndFlush(validRecords);
	}

	private void processRow(Row r) { // NOSONAR

		boolean isValidRow = true;

		LocalDateTime dateTime = null;  // 0

		// Failure Detail
		int eventId = 0;				// 1
		int causeCode = 0;				// 8
		int failureCode = 0;			// 2
		int duration = 0;				// 7

		// Cell Detail
		int cellId = 0;					// 6
		long tac = 0l;					// 3
		int mcc = 0;					// 4
		int mnc = 0;					// 5
		String neVersion = null;		// 9

		// Subscriber Detail
		long imsi = 0l;					// 10
		long hier3Id = 0l;				// 11
		long hier32Id = 0l;				// 12
		long hier321Id = 0l;			// 13

		for (Cell c : r) {
			if (c != null) {
				int index = c.getColumnIndex();

				switch (c.getCellType()) {
				case NUMERIC:
					switch (index) {
					case 1:
						eventId = (int) c.getNumericCellValue();
						break;
					case 2:
						failureCode = (int) c.getNumericCellValue();
						break;
					case 3:
						tac = (long) c.getNumericCellValue();
						break;
					case 4:
						mcc = (int) c.getNumericCellValue();
						break;
					case 5:
						mnc = (int) c.getNumericCellValue();
						break;
					case 6:
						cellId = (int) c.getNumericCellValue();
						break;
					case 7:
						duration = (int) c.getNumericCellValue();
						break;
					case 8:
						causeCode = (int) c.getNumericCellValue();
						break;
					case 10:
						imsi = (long) c.getNumericCellValue();
						break;
					case 11:
						hier3Id = (long) c.getNumericCellValue();
						break;
					case 12:
						hier32Id = (long) c.getNumericCellValue();
						break;
					case 13:
						hier321Id = (long) c.getNumericCellValue();
						break;
					default:
						isValidRow = false;
					}
					break;

				case STRING:
					if (index == 0) {
						dateTime = c.getLocalDateTimeCellValue();

					} else if (index == 9) {
						neVersion = c.getStringCellValue();
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
			validRecords.add(new CallFailure(dateTime, new FailureDetail(eventId, causeCode, failureCode, duration),
					new CellDetail(cellId, tac, mcc, mnc, neVersion),
					new SubscriberDetail(imsi, hier3Id, hier32Id, hier321Id)));
		}
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}
}
