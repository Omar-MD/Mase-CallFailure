package com.tus.cipher.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.dao.EventCauseDAO;
import com.tus.cipher.dao.FailureClassDAO;
import com.tus.cipher.dao.MccMncDAO;
import com.tus.cipher.dao.UeDAO;
import com.tus.cipher.services.sheets.BaseDataSheet;
import com.tus.cipher.services.sheets.BaseSheetProcessor;
import com.tus.cipher.services.sheets.EventCauseSheet;
import com.tus.cipher.services.sheets.FailureClassSheet;
import com.tus.cipher.services.sheets.MccMncSheet;
import com.tus.cipher.services.sheets.UeSheet;

@Service
public class ImportService {
	private static final String ROOT_PATH = "src/main/resources/";

	// Sheet Processors
	private BaseSheetProcessor baseDataSheet;
	private List<BaseSheetProcessor> refProcessors = new ArrayList<>();

	private ValidationService validator;

	public ImportService(CallFailureDAO callFailureDAO, MccMncDAO mccMncDAO, UeDAO ueDAO, FailureClassDAO failureClassDAO, EventCauseDAO eventCauseDAO) {
		this.baseDataSheet = new BaseDataSheet(callFailureDAO);
		this.refProcessors.add(new MccMncSheet(mccMncDAO));
		this.refProcessors.add(new UeSheet(ueDAO));
		this.refProcessors.add(new FailureClassSheet(failureClassDAO));
		this.refProcessors.add(new EventCauseSheet(eventCauseDAO));
		this.validator = new ValidationService(mccMncDAO, ueDAO, failureClassDAO, eventCauseDAO);
	}

	@Transactional
	public void importFile(String filename) throws IOException {

		try (HSSFWorkbook workbook = (HSSFWorkbook) WorkbookFactory.create(new File(ROOT_PATH + filename))) {

			// Import Reference Sheets
			for (BaseSheetProcessor proc : refProcessors) {
				HSSFSheet sheet = workbook.getSheet(proc.getSheetName());
				importSheet(proc, sheet);
			}

			// Prepare & Set Base Data Sheet Validator
			validator.prepareValidator();
			((BaseDataSheet) baseDataSheet).setValidator(validator);

			// Import BaseData
			HSSFSheet sheet = workbook.getSheet(baseDataSheet.getSheetName());
			importSheet(baseDataSheet, sheet);

		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Error processing Excel file: " + e.getMessage());
		}
	}

	private void importSheet(BaseSheetProcessor proc, HSSFSheet sheet) {

		int totalRows = sheet.getPhysicalNumberOfRows();
		System.out.println("=>" + sheet.getSheetName() + ", row(s): " + totalRows);

		// Skip Header Row
		for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
			Row r = sheet.getRow(rowIndex);
			if (r != null) {
				proc.processRow(r);
			}
		}
		// SaveBatches
		proc.saveInBatchs();
	}
}
