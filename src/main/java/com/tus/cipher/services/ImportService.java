package com.tus.cipher.services;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tus.cipher.services.sheets.BaseDataSheet;
import com.tus.cipher.services.sheets.BaseSheetProcessor;

@Service
public class ImportService {

	private BaseSheetProcessor baseDataSheet;
	private List<BaseSheetProcessor> refProcessors;
	private DataValidator validator;

	public ImportService(List<BaseSheetProcessor> refProcessors, DataValidator validator,
			BaseSheetProcessor baseDataSheet) {
		this.refProcessors = refProcessors;
		this.validator = validator;
		this.baseDataSheet = baseDataSheet;
	}

	@Transactional
	public void importWorkBook(HSSFWorkbook workbook){
			// Setup Logger
			LoggerService.setLogFolderPath("logs");
			LoggerService.setLogFilePath("logs/import_log");

			importReferenceSheets(workbook);
			importBaseData(workbook);
	}

	void importReferenceSheets(HSSFWorkbook workbook) {
		for (BaseSheetProcessor proc : refProcessors) {
			HSSFSheet sheet = workbook.getSheet(proc.getSheetName());
			importSheet(proc, sheet);
		}
	}

	void importBaseData(HSSFWorkbook workbook) {
		validator.prepareValidator();				// Prepare & Set Validation
		((BaseDataSheet) baseDataSheet).setValidator(validator);
		HSSFSheet sheet = workbook.getSheet(baseDataSheet.getSheetName());
		importSheet(baseDataSheet, sheet);
	}

	void importSheet(BaseSheetProcessor proc, HSSFSheet sheet) {
		int totalRows = sheet.getPhysicalNumberOfRows();

		System.out.println("\nSheet: "+ sheet.getSheetName());
		System.out.println("=> row(s): " + totalRows);

		// Skip Header Row
		for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
			Row r = sheet.getRow(rowIndex);
			if (r != null) {
				proc.processRow(r);
			}
		}
		proc.saveInBatchs();
	}
}
