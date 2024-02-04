package com.tus.cipher.services.sheets;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.FailureClassDAO;
import com.tus.cipher.dto.sheets.FailureClass;
import com.tus.cipher.services.LoggerService;

@Component
public class FailureClassSheet extends BaseSheetProcessor{

	private static final String SHEET_NAME = "Failure Class Table";
	private static final int MAX_BATCH_SIZE = 128;

	private FailureClassDAO failureClassDAO;
	List<FailureClass> validRows = new ArrayList<>();

	public FailureClassSheet(FailureClassDAO failureClassDAO) {
		this.failureClassDAO = failureClassDAO;
	}

	@Override
	public void processRow(Row r) {

		try {
			int failureCode = (int) r.getCell(0).getNumericCellValue();
			String description = r.getCell(1).getStringCellValue();

			FailureClass failureClass = new FailureClass(failureCode, description);
			validRows.add(failureClass);

		} catch (Exception e) {
			LoggerService.logInfo("sysadmin/import", "FailureClassSheet:processRow", e.getMessage());
		}
	}

	@Override
	public void saveInBatchs() {
		int totalRows = validRows.size();
		List<FailureClass> rowsToSave = new ArrayList<>(validRows);

		for (int i = 0; i < totalRows; i += MAX_BATCH_SIZE) {
			List<FailureClass> batchEntities = rowsToSave.subList(i, Math.min(i + MAX_BATCH_SIZE, totalRows));
			failureClassDAO.saveAllAndFlush(batchEntities);
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
