package com.tus.cipher.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.CallFailureDAO;

@Component
public class ImportBaseData implements SheetProcessor {

	private static final String SHEET_NAME = "Base Data";

	@Autowired
	CallFailureDAO callFailureDAO;

	@Override
	public void processSheet(HSSFSheet sheet) {
		// 0 Date time day/date/year hour:min
		// 1 event id (int)
		// 2 failure class (int)
		// 3 ue type (int) long
		// 4 market (int)
		// 5 operator (int)
		// 6 cell id (int)
		// 7 duration (int)
		// 8 cause code (int)
		// 9 ne version (string)
		// 10 imsi (long)
		// 11 hier3 (int)
		// 12 hier32 (int)
		// 13 hier321 (int)

    	List<Row> validRecords = new ArrayList<>();		// List of valid rows
    	List<Row> invalidRecords = new ArrayList<>();	// List of invalid rows

        // Process rows one by one
        for (Row row : sheet) {
            // Validate row
            if (ValidationService.validateRow(row)) {
            	validRecords.add(row);

            } else {
                invalidRecords.add(row);
            }
        }


        // Log invalid Rows to logFile
        // Create List<CallFailure> from List<Row>
        // and batch save to database
	}

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}

}
