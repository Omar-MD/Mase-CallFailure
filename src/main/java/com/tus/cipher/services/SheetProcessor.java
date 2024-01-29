package com.tus.cipher.services;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface SheetProcessor {

	String getSheetName();

	void processSheet(HSSFSheet sheet);

}
