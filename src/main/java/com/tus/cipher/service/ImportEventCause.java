package com.tus.cipher.service;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.EventCauseDAO;

@Component
public class ImportEventCause implements SheetProcessor {

	private static final String SHEET_NAME = "Event-Cause Table";

	@Autowired
	EventCauseDAO eventCauseDAO;

	@Override
	public void processSheet(HSSFSheet sheet) {
		// 0 course code (int)
		// 1 event id (int)
		// 2 description (string)
		System.out.println(sheet.getSheetName());
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}


}
