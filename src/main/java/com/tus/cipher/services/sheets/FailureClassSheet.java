package com.tus.cipher.services.sheets;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.FailureClassDAO;
import com.tus.cipher.dto.sheets.FailureClass;

@Component
public class FailureClassSheet implements SheetProcessor<FailureClass> {

	private static final String SHEET_NAME = "Failure Class Table";

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}

	@Autowired
	FailureClassDAO failureClassDAO;

	@Override
	public FailureClassDAO getDAO() {
		return failureClassDAO;
	}

	@Override
	public Class<FailureClass> getType() {
		return FailureClass.class;
	}

	@Override
	public FailureClass processRow(Row r) {
		FailureClass failureClass = null;

		Integer failureCode = null;
		String description = null;

		try {
			failureCode = (int) r.getCell(0).getNumericCellValue();
			description = r.getCell(1).getStringCellValue();

		} catch (Exception e) {
			return null;
		}

		if (failureCode != null && description != null) {
			failureClass = new FailureClass(failureCode, description);
		}

		return failureClass;
	}
}
