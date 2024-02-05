package com.tus.cipher.services.sheets;

import org.apache.poi.ss.usermodel.Row;

public abstract class BaseSheetProcessor {

	public abstract String getSheetName();

	public abstract int getBatchSize();

    public abstract void processRow(Row r);

    public abstract void saveInBatchs();
}