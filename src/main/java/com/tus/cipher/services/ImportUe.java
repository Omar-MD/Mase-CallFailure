package com.tus.cipher.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tus.cipher.dao.UeDAO;
import com.tus.cipher.dto.Ue;

@Component
public class ImportUe implements SheetProcessor {

	private static final String SHEET_NAME = "UE Table";
	private List<Ue> validRecords = new ArrayList<>();

	@Autowired
	UeDAO ueDAO;

	@Transactional
	@Override
	public void processSheet(HSSFSheet sheet) {

		int totalRows = sheet.getPhysicalNumberOfRows();
		System.out.println("=>" + sheet.getSheetName() + "\t\t\trow(s): " + totalRows);

		// Skip Header Row
		for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
			Row r = sheet.getRow(rowIndex);
			if (r != null) {
				processRow(r);
			}
		}

		ueDAO.saveAllAndFlush(validRecords);
	}

	private void processRow(Row r) { //NOSONAR

		boolean isValidRow = true;
		Long tac = 0L;
		String marketingName = null;
		String manufacturer = null;
		String accessCapability = null;

		for (Cell c : r) {
			if (c != null) {
				int index = c.getColumnIndex();

				switch (c.getCellType()) {
				case NUMERIC:
					if(index == 0) {
						tac = (long) c.getNumericCellValue();
					}

					break;

				case STRING:
					if(index == 1){
						marketingName = c.getStringCellValue();
					}
					else if(index == 2){
						manufacturer = c.getStringCellValue();
					}
					else if(index == 3) {
						accessCapability = c.getStringCellValue();
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
			validRecords.add(new Ue(tac, marketingName, manufacturer, accessCapability));
		}
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}
}
