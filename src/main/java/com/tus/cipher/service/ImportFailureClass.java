package com.tus.cipher.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tus.cipher.dao.FailureClassDAO;
import com.tus.cipher.dto.FailureClass;

@Component
public class ImportFailureClass implements SheetProcessor {

	private static final String SHEET_NAME = "Failure Class Table";
	private List<FailureClass> validRecords = new ArrayList<>();

	@Autowired
	FailureClassDAO failureClassDAO;

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

		failureClassDAO.saveAllAndFlush(validRecords);
	}

	private void processRow(Row r) { //NOSONAR

		boolean isValidRow = true;
		Integer failureCode = 0;
		String description = null;

		for (Cell c : r) {
			if (c != null) {
				int index = c.getColumnIndex();

				switch (c.getCellType()) {
				case NUMERIC:
					if(index == 0) {
						failureCode = (int) c.getNumericCellValue();
					}
					break;

				case STRING:
					if(index == 1){
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
			validRecords.add(new FailureClass(failureCode, description));
		}
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}

}
