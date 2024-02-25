package com.tus.cipher.services.sheets;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.UeDAO;
import com.tus.cipher.dto.sheets.Ue;
import com.tus.cipher.services.LoggerService;

@Component
public class UeSheet extends BaseSheetProcessor{

	private static final String SHEET_NAME = "UE Table";
	private static final int MAX_BATCH_SIZE = 128;

	private final UeDAO ueDAO;
	List<Ue> validRows = new ArrayList<>();

	public UeSheet(UeDAO ueDAO) {
		this.ueDAO = ueDAO;
	}

	@Override
	public void processRow(Row r) {

		try {
			Long tac = (long) r.getCell(0).getNumericCellValue();
			String marketingName = r.getCell(1).getStringCellValue();
			String manufacturer = r.getCell(2).getStringCellValue();
			String accessCapability = r.getCell(3).getStringCellValue();

			Ue ue = new Ue(tac, marketingName, manufacturer, accessCapability);
			validRows.add(ue);

		} catch (Exception e) {
			LoggerService.logInfo("sysadmin/import", "UeSheet:processRow", e.getMessage(), r.getRowNum());
		}
	}

	@Override
	public void saveInBatchs() {
		int totalRows = validRows.size();
		List<Ue> rowsToSave = new ArrayList<>(validRows);

		for (int i = 0; i < totalRows; i += MAX_BATCH_SIZE) {
			List<Ue> batchEntities = rowsToSave.subList(i, Math.min(i + MAX_BATCH_SIZE, totalRows));
			ueDAO.saveAllAndFlush(batchEntities);
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
