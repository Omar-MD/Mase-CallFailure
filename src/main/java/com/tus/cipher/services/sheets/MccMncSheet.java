package com.tus.cipher.services.sheets;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.MccMncDAO;
import com.tus.cipher.dto.sheets.MccMnc;

@Component
public class MccMncSheet implements SheetProcessor<MccMnc> {

	private static final String SHEET_NAME = "MCC - MNC Table";

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}

	@Autowired
	private MccMncDAO mccMncDAO;

	@Override
	public MccMncDAO getDAO() {
		return mccMncDAO;
	}

	@Override
	public Class<MccMnc> getType() {
		return MccMnc.class;
	}

	@Override
	public MccMnc processRow(Row r) {
		MccMnc mccMnc = null;

		Integer mcc = null;
		Integer mnc = null;
		String country = null;
		String operator = null;

		try {
			mcc = (int) r.getCell(0).getNumericCellValue();
			mnc = (int) r.getCell(1).getNumericCellValue();
			country = r.getCell(2).getStringCellValue();
			operator = r.getCell(3).getStringCellValue();

		} catch (Exception e) {
			return null;
		}

		if (mcc != null && mnc != 0 && country != null && operator != null) {
			mccMnc = new MccMnc(mcc, mnc, country, operator);
		}
		return mccMnc;
	}
}
