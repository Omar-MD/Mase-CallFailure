package com.tus.cipher.services.sheets;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.dto.sheets.CallFailure;
import com.tus.cipher.services.DataValidator;

@Component
public class BaseDataSheet extends BaseSheetProcessor {

	private static final String SHEET_NAME = "Base Data";
	private static final int MAX_BATCH_SIZE = 512;

	private final CallFailureDAO callFailureDAO;
	private DataValidator validator;
	List<CallFailure> validRows = new ArrayList<>();

	public BaseDataSheet(CallFailureDAO callFailureDAO) {
		this.callFailureDAO = callFailureDAO;
	}

	public void setValidator(DataValidator validator) {
		this.validator = validator;
	}

	@Override
	public void processRow(Row r) {

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
			this.validator.validate(dateTime, eventId, causeCode, failureCode, duration, cellId, tac, mcc, mnc,
					neVersion, imsi, hier3Id, hier32Id, hier321Id);

			// Create new Call Failure Entry
			CallFailure callFailure = new CallFailure(dateTime, eventId, causeCode, failureCode, duration, cellId, tac, mcc, mnc,
					neVersion, imsi, hier3Id, hier32Id, hier321Id);

			validRows.add(callFailure);

		} catch (Exception e){
			// TODO: Error: Log Parsing/Validation errors
//			System.out.println("row: " + r.getRowNum() + ", error: " + e.getMessage());
		}
	}

	@Override
	public void saveInBatchs() {
		int totalRows = validRows.size();
		System.out.println("Total size: " + totalRows);
		List<CallFailure> rowsToSave = new ArrayList<>(validRows);

		for (int i = 0; i < totalRows; i += MAX_BATCH_SIZE) {
			List<CallFailure> batchEntities = rowsToSave.subList(i, Math.min(i + MAX_BATCH_SIZE, totalRows));
			System.out.println("Batch size: " + batchEntities.size());
			callFailureDAO.saveAll(batchEntities);
		}

		validRows.clear();
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}

	@Override
	public int getBatchSize() {
		return MAX_BATCH_SIZE;
	}
}
