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

	BaseSheetProcessor baseDataSheet;
	List<BaseSheetProcessor> refProcessors;
	DataValidator validator;

	public ImportService(ImportParams importParams) {
		this.refProcessors = importParams.getRefProcessors();
		this.validator = importParams.getDataValidator();
		this.baseDataSheet = importParams.getBaseDataSheet();
	}

	@Transactional
	public void importWorkBook(HSSFWorkbook workbook){
			System.out.println("\n\nImport in progress...");
			importReferenceSheets(workbook);
			importBaseData(workbook);
			System.out.println("*** Import complete! ***\n\n");
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
