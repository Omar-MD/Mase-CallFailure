package com.tus.cipher.services.sheets;

import java.time.LocalDateTime;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.dto.sheets.CallFailure;

@Component
public class BaseDataSheet implements SheetProcessor<CallFailure> {

	private static final String SHEET_NAME = "Base Data";

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}

	@Autowired
	CallFailureDAO callFailureDAO;

	@Override
	public CallFailureDAO getDAO() {
		return callFailureDAO;
	}

	@Override
	public Class<CallFailure> getType() {
		return CallFailure.class;
	}

	@Override
	public CallFailure processRow(Row r) {
		CallFailure callFailure = null;
		try {
			LocalDateTime dateTime = r.getCell(0).getLocalDateTimeCellValue();
			int eventId = (int) r.getCell(1).getNumericCellValue();
			int failureCode = (int) r.getCell(2).getNumericCellValue();
			long tac = (long) r.getCell(3).getNumericCellValue();
			int mcc = (int) r.getCell(4).getNumericCellValue();
			int mnc = (int) r.getCell(5).getNumericCellValue();
			int cellId = (int) r.getCell(6).getNumericCellValue();
			int duration = (int) r.getCell(7).getNumericCellValue();
			int causeCode = (int) r.getCell(8).getNumericCellValue();
			String neVersion = r.getCell(9).getStringCellValue();
			long imsi = (long) r.getCell(10).getNumericCellValue();
			long hier3Id = (long) r.getCell(11).getNumericCellValue();
			long hier32Id = (long) r.getCell(12).getNumericCellValue();
			long hier321Id = (long) r.getCell(13).getNumericCellValue();

			// Perform Validation
			if (isValidData(eventId, causeCode, failureCode, duration, cellId, tac, mcc, mnc, neVersion, imsi, hier3Id,
					hier32Id, hier321Id)) {

				callFailure = new CallFailure(dateTime, eventId, causeCode, failureCode, duration, cellId, tac, mcc,
						mnc, neVersion, imsi, hier3Id, hier32Id, hier321Id);
			} else {
				// TODO: Log validation error

			}
		} catch (Exception e) {
			// TODO: Error Log Parsing error

		}

		return callFailure;
	}

	private boolean isValidData(int eventId, int causeCode, int failureCode, int duration, int cellId, long tac, // NOSONAR
			int mcc, int mnc, String neVersion, long imsi, long hier3Id, long hier32Id, long hier321Id) {

		return eventId > 0 && causeCode > 0 && failureCode > 0 && duration > 0 && cellId > 0 && tac > 0 && mcc > 0
				&& mnc > 0 && neVersion != null && !neVersion.isEmpty() && imsi > 0 && hier3Id > 0 && hier32Id > 0
				&& hier321Id > 0;
	}
}
