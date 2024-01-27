package com.tus.cipher.services;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

@Service
public class ImportService {

	private static final String ROOT_PATH = "src/main/resources/";

	public static void readExcel(String fileName) throws IOException {

		FileInputStream file = new FileInputStream(ROOT_PATH + fileName);

		try (Workbook wb = WorkbookFactory.create(file)) {

			// Get the number of sheets
			int numberOfSheets = wb.getNumberOfSheets();

			while (--numberOfSheets >= 0) {
				Sheet sheet = wb.getSheetAt(numberOfSheets);
				processSheet(sheet);
			}
		}

	}

	private static void processSheet(Sheet sheet) {
		switch (sheet.getSheetName()) {
		case "Base Data":
			readBaseData(sheet);
			break;
		case "Event-Cause Table":
			readEventCauseTable(sheet);
			break;
		case "Failure Class Table":
			readFailureClassTable(sheet);
			break;
		case "UE Table":
			readUETable(sheet);
			break;
		case "MCC - MNC Table":
			readMCCTable(sheet);
			break;
		default:
			break;
		}
	}

	private static void readBaseData(Sheet sheet) {
		System.out.println("Processing ("+sheet.getSheetName()+")...");
		sheet.forEach(row -> {

			row.forEach(cell->{
				System.out.println(cell.getCellType()); //Numeric or String
			});
		});
	}

	private static void readEventCauseTable(Sheet sheet) {
		System.out.println("Processing ("+sheet.getSheetName()+")...");
		sheet.forEach(row -> {

			row.forEach(cell->{
				System.out.println(cell.getCellType()); //Numeric or String
			});
		});
	}

	private static void readFailureClassTable(Sheet sheet) {
		System.out.println("Processing ("+sheet.getSheetName()+")...");
		sheet.forEach(row -> {

			row.forEach(cell->{
				System.out.println(cell.getCellType()); //Numeric or String
			});
		});
	}

	private static void readUETable(Sheet sheet) {
		System.out.println("Processing ("+sheet.getSheetName()+")...");
		sheet.forEach(row -> {

			row.forEach(cell->{
				System.out.println(cell.getCellType()); //Numeric or String
			});
		});
	}

	private static void readMCCTable(Sheet sheet) {
		System.out.println("Processing ("+sheet.getSheetName()+")...");

		sheet.forEach(row -> {

			row.forEach(cell->{
				System.out.println(cell.getCellType()); //Numeric or String
			});
		});
	}
}
