package com.tus.cipher.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tus.cipher.dao.MccMncDAO;
import com.tus.cipher.dto.MccMnc;

@Component
public class ImportMccMnc implements SheetProcessor {

	private static final String SHEET_NAME = "MCC - MNC Table";
	private List<MccMnc> validRecords = new ArrayList<>();

	@Autowired
	private MccMncDAO mccMncDAO;

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

		mccMncDAO.saveAllAndFlush(validRecords); // Perform batch insert
	}

	private void processRow(Row r) {// NOSONAR

		boolean isValidRow = true;
		int mcc = 0;
		int mnc = 0;
		String country = null;
		String operator = null;

		for (Cell c : r) {
			if (c != null) {
				int columnIndex = c.getColumnIndex();

				switch (c.getCellType()) {
				case NUMERIC:
					if (columnIndex == 0) {
						mcc = (int) c.getNumericCellValue();

					} else if (columnIndex == 1) {
						mnc = (int) c.getNumericCellValue();
					}
					break;
				case STRING:
					if (columnIndex == 2) {
						country = c.getStringCellValue();

					} else if (columnIndex == 3) {
						operator = c.getStringCellValue();
					}
					break;
				default:
					isValidRow = false;
					break;
				}

			} else {
				isValidRow = false;
				break;
			}
		}

		if (isValidRow)
			validRecords.add(new MccMnc(mcc, mnc, country, operator));
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}
}
