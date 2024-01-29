package com.tus.cipher.services;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImportService {

	@Autowired
	ImportMccMnc importMccMnc;

	@Autowired
	ImportUe importUe;

	@Autowired
	ImportFailureClass importFailureClass;

	@Autowired
	ImportEventCause importEventCause;

	@Autowired
	ImportBaseData importBaseData;

	@Transactional
	public void importFile(String filename) throws IOException {
		List<SheetProcessor> refProcessors = Arrays.asList(importMccMnc, importUe, importFailureClass, importEventCause);
		SheetProcessor baseProcessor = importBaseData;

		try (HSSFWorkbook workbook = (HSSFWorkbook) WorkbookFactory.create(new File("src/main/resources/" + filename))) {

			// Process Reference Sheets
			for (SheetProcessor processor : refProcessors) {
				HSSFSheet sheet = workbook.getSheet(processor.getSheetName());
				processor.processSheet(sheet);
			}

			// Process Base Data
			HSSFSheet sheet = workbook.getSheet(baseProcessor.getSheetName());
			baseProcessor.processSheet(sheet);

		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Error reading or processing Excel file: " + e.getMessage());
		}
	}
}