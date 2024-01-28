package com.tus.cipher.service;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.FailureClassDAO;

@Component
public class ImportFailureClass implements SheetProcessor {

	private static final String SHEET_NAME = "Failure Class Table";

	@Autowired
	FailureClassDAO failureClassDAO;

	@Override
	public void processSheet(HSSFSheet sheet) {
		// 0 failure Class (int)
		// 1 description (string)

		System.out.println(sheet.getSheetName());
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}

}
