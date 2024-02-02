package com.tus.cipher.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tus.cipher.dao.EventCauseDAO;
import com.tus.cipher.dao.FailureClassDAO;
import com.tus.cipher.dao.MccMncDAO;
import com.tus.cipher.dao.UeDAO;
import com.tus.cipher.dto.sheets.CallFailure;
import com.tus.cipher.dto.sheets.EventCause;
import com.tus.cipher.dto.sheets.FailureClass;
import com.tus.cipher.dto.sheets.MccMnc;
import com.tus.cipher.dto.sheets.Ue;
import com.tus.cipher.services.sheets.BaseDataSheet;
import com.tus.cipher.services.sheets.SheetProcessor;

@Service
public class ImportService {
	private static final String ROOT_PATH = "src/main/resources/";
	private static final int MIN_BATCH_SIZE = 64;
	private static final int MAX_BATCH_SIZE = 256;

	private final SheetProcessor<MccMnc> importMccMnc;
	private final SheetProcessor<Ue> importUe;
	private final SheetProcessor<FailureClass> importFailureClass;
	private final SheetProcessor<EventCause> importEventCause;
	private final SheetProcessor<CallFailure> importBaseData;

	public ImportService(SheetProcessor<MccMnc> importMccMnc, SheetProcessor<Ue> importUe,
			SheetProcessor<FailureClass> importFailureClass, SheetProcessor<EventCause> importEventCause,
			SheetProcessor<CallFailure> importBaseData) {
		this.importBaseData = importBaseData;
		this.importMccMnc = importMccMnc;
		this.importUe = importUe;
		this.importFailureClass = importFailureClass;
		this.importEventCause = importEventCause;
	}

	@Transactional
	public void importFile(String filename) throws IOException {

		List<SheetProcessor<?>> refProcessors = Arrays.asList(importMccMnc, importUe, importFailureClass,
				importEventCause);

		try (HSSFWorkbook workbook = (HSSFWorkbook) WorkbookFactory.create(new File(ROOT_PATH + filename))) {

			// Import Reference Sheets
			for (SheetProcessor<?> proc : refProcessors) {
				HSSFSheet sheet = workbook.getSheet(proc.getSheetName());
				importSheet(proc, sheet);
			}

			// Set ValidationService
			((BaseDataSheet) importBaseData).setValidator(
					new ValidationService((EventCauseDAO) importEventCause.getDAO(), (MccMncDAO) importMccMnc.getDAO(),
							(FailureClassDAO) importFailureClass.getDAO(), (UeDAO) importUe.getDAO()));

			// Import BaseData
			HSSFSheet sheet = workbook.getSheet(importBaseData.getSheetName());
			importSheet(importBaseData, sheet);

		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Error processing Excel file: " + e.getMessage());
		}
	}

	private <T> void importSheet(SheetProcessor<T> processor, HSSFSheet sheet) {
		List<T> entities = new ArrayList<>();

		int totalRows = sheet.getPhysicalNumberOfRows();
		System.out.println("=>" + sheet.getSheetName() + ", row(s): " + totalRows);

		// Skip Header Row
		for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
			Row r = sheet.getRow(rowIndex);
			if (r != null) {
				// Fetch Entity
				T entity = processor.processRow(r);
				if (entity != null) {
					// Add to List
					entities.add(entity);
				}
			}
		}

		int batchSize = MIN_BATCH_SIZE;
		batchSize = Math.min(batchSize, MAX_BATCH_SIZE);

		for (int i = 0; i < entities.size(); i += batchSize) {
			List<T> batchEntities = entities.subList(i, Math.min(i + batchSize, entities.size()));
			processor.getDAO().saveAll(batchEntities);
		}
	}
}