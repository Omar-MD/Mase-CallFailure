package com.tus.cipher.service;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.UeDAO;

@Component
public class ImportUe implements SheetProcessor {

	private static final String SHEET_NAME = "UE Table";

	@Autowired
	UeDAO ueDAO;

	@Override
	public void processSheet(HSSFSheet sheet) {

		// 0 tac (int)
		// 1 marketingName (string)
		// 2 manufacturer (string)
		// 3 access capability (string)

		System.out.println(sheet.getSheetName());
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}

}
