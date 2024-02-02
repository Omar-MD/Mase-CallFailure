package com.tus.cipher.services.sheets;

import java.time.LocalDateTime;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.dto.sheets.CallFailure;
import com.tus.cipher.services.ValidationService;

@Component
public class BaseDataSheet implements SheetProcessor<CallFailure> {

	private static final String SHEET_NAME = "Base Data";

	@Autowired
	CallFailureDAO callFailureDAO;
	ValidationService dataValidator;

	public void setValidator(ValidationService dataValidator) {
		this.dataValidator = dataValidator;
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}

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
			this.dataValidator.validate(dateTime, eventId, causeCode, failureCode, duration, cellId, tac, mcc, mnc,
					neVersion, imsi, hier3Id, hier32Id, hier321Id);

			callFailure = new CallFailure(dateTime, eventId, causeCode, failureCode, duration, cellId, tac, mcc, mnc,
					neVersion, imsi, hier3Id, hier32Id, hier321Id);

		} catch (Exception e){
			// TODO: Error: Log Parsing/Validation errors

		}
		return callFailure;
	}
}
