package com.tus.cipher.services;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tus.cipher.dao.UeDAO;
import com.tus.cipher.dto.Ue;

@Component
public class ImportUe implements SheetProcessor<Ue> {

	private static final String SHEET_NAME = "UE Table";

	@Override
	public String getSheetName() {
		return SHEET_NAME;
	}

	@Autowired
	UeDAO ueDAO;

	@Override
	public UeDAO getDAO() {
		return ueDAO;
	}

	@Override
	public Class<Ue> getType() {
		return Ue.class;
	}

	@Override
	public Ue processRow(Row r) {
		Ue ue = null;

		Long tac = null;
		String marketingName = null;
		String manufacturer = null;
		String accessCapability = null;

		try {
			tac = (long) r.getCell(0).getNumericCellValue();
			marketingName = r.getCell(1).getStringCellValue();
			manufacturer = r.getCell(2).getStringCellValue();
			accessCapability = r.getCell(3).getStringCellValue();

		} catch (Exception e) {
			return null;
		}

		if (tac != null && marketingName != null && manufacturer != null && accessCapability != null) {
			ue = new Ue(tac, marketingName, manufacturer, accessCapability);
		}
		return ue;
	}
}
