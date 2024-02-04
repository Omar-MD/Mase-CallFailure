package com.tus.cipher.services.sheets;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.MccMncDAO;
import com.tus.cipher.dto.sheets.MccMnc;
import com.tus.cipher.services.LoggerService;

@Component
public class MccMncSheet extends BaseSheetProcessor{

	private static final String SHEET_NAME = "MCC - MNC Table";
	private static final int MAX_BATCH_SIZE = 128;

	private MccMncDAO mccMncDAO;
	List<MccMnc> validRows = new ArrayList<>();

	public MccMncSheet(MccMncDAO mccMncDAO) {
		this.mccMncDAO = mccMncDAO;
	}

	@Override
	public void processRow(Row r) {

		try {
			Integer mcc = (int) r.getCell(0).getNumericCellValue();
			Integer mnc = (int) r.getCell(1).getNumericCellValue();
			String country = r.getCell(2).getStringCellValue();
			String operator = r.getCell(3).getStringCellValue();

			MccMnc mccMnc = new MccMnc(mcc, mnc, country, operator);
			validRows.add(mccMnc);

		} catch (Exception e) {
			LoggerService.logInfo("sysadmin/import", "MccMncSheet:processRow", e.getMessage());
		}
	}

	@Override
	public void saveInBatchs() {
		int totalRows = validRows.size();
		List<MccMnc> rowsToSave = new ArrayList<>(validRows);

		for (int i = 0; i < totalRows; i += MAX_BATCH_SIZE) {
			List<MccMnc> batchEntities = rowsToSave.subList(i, Math.min(i + MAX_BATCH_SIZE, totalRows));
			mccMncDAO.saveAllAndFlush(batchEntities);
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
