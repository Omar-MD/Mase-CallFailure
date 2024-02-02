package com.tus.cipher.services.sheets;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetProcessor<T> {

	String getSheetName();

	T processRow(Row r);

	Class<T> getType();

	JpaRepository<T, Long> getDAO();
}
