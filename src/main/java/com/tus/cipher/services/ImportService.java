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

import com.tus.cipher.dto.CallFailure;
import com.tus.cipher.dto.EventCause;
import com.tus.cipher.dto.FailureClass;
import com.tus.cipher.dto.MccMnc;
import com.tus.cipher.dto.Ue;

@Service
public class ImportService {
	private static final String ROOT_PATH = "src/main/resources/";
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

			// Import Ref Sheets
			for (SheetProcessor<?> proc : refProcessors) {
				HSSFSheet sheet = workbook.getSheet(proc.getSheetName());
				importSheet(proc, sheet);
			}

			HSSFSheet sheet = workbook.getSheet(importBaseData.getSheetName());
			importSheet(importBaseData, sheet);

		} catch (IOException e) {
			// TODO: Improve Failure Response
			e.printStackTrace();
			throw new IOException("Error reading or processing Excel file: " + e.getMessage());
		}
	}

	public <T> void importSheet(SheetProcessor<T> processor, HSSFSheet sheet) {
		List<T> entities = new ArrayList<>();

		int totalRows = sheet.getPhysicalNumberOfRows();
		System.out.println("=>" + sheet.getSheetName() + "\t\t\t\trow(s): " + totalRows);

		// Skip Header Row
		for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
			Row r = sheet.getRow(rowIndex);
			if (r != null) {
				T entity = processor.processRow(r);
				if (entity != null) {
					entities.add(entity);
				}
			}
		}
		processor.getDAO().saveAllAndFlush(entities);
	}
}