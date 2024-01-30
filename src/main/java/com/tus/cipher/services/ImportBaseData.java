package com.tus.cipher.services;

import java.time.LocalDateTime;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.dto.CallFailure;

@Component
public class ImportBaseData implements SheetProcessor<CallFailure> {

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

		LocalDateTime dateTime = null;

		// Failure Detail
		Integer eventId = null;
		Integer causeCode = null;
		Integer failureCode = null;
		Integer duration = null;

		// Cell Detail
		Integer cellId = null;
		Long tac = null;
		Integer mcc = null;
		Integer mnc = null;
		String neVersion = null;

		// Subscriber Detail
		Long imsi = null;
		Long hier3Id = null;
		Long hier32Id = null;
		Long hier321Id = null;

		try {
			dateTime = r.getCell(0).getLocalDateTimeCellValue();

			eventId = (int) r.getCell(1).getNumericCellValue();
			failureCode = (int) r.getCell(2).getNumericCellValue();

			tac = (long) r.getCell(3).getNumericCellValue();

			mcc = (int) r.getCell(4).getNumericCellValue();
			mnc = (int) r.getCell(5).getNumericCellValue();
			cellId = (int) r.getCell(6).getNumericCellValue();
			duration = (int) r.getCell(7).getNumericCellValue();
			causeCode = (int) r.getCell(8).getNumericCellValue();

			neVersion = r.getCell(9).getStringCellValue();

			imsi = (long) r.getCell(10).getNumericCellValue();
			hier3Id = (long) r.getCell(11).getNumericCellValue();
			hier32Id = (long) r.getCell(12).getNumericCellValue();
			hier321Id = (long) r.getCell(13).getNumericCellValue();

		} catch (Exception e) {
			return null;
		}

		// TODO: Perform Validation
		if (eventId != null && causeCode != null && failureCode != null && duration != null && cellId != null
				&& tac != null && mcc != null && mnc != null && neVersion != null && imsi != null && hier3Id != null
				&& hier32Id != null && hier321Id != null) {

			callFailure = new CallFailure(dateTime, eventId, causeCode, failureCode, duration, cellId, tac, mcc, mnc,
					neVersion, imsi, hier3Id, hier32Id, hier321Id);
		}

		return callFailure;
	}

}
