package com.tus.cipher.services.sheets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tus.cipher.dao.MccMncDAO;
import com.tus.cipher.dto.sheets.MccMnc;

class MccMncSheetTest {

    private MccMncSheet mccMncSheet;
    private MccMncDAO mccMncDAO;
    private Row rowMock;

    @BeforeEach
    public void setUp() {
        mccMncDAO = mock(MccMncDAO.class);
        mccMncSheet = new MccMncSheet(mccMncDAO);
        rowMock = mock(Row.class);
    }

    @Test
    void testSheetNameAndBatchSize() {
        assertEquals("MCC - MNC Table", mccMncSheet.getSheetName());
        assertEquals(128, mccMncSheet.getBatchSize());
    }

    @Test
    void testProcessRow_ValidRow() {
    	when(rowMock.getCell(0)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(0).getNumericCellValue()).thenReturn(123.0);
        when(rowMock.getCell(1)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(1).getNumericCellValue()).thenReturn(456.0);
        when(rowMock.getCell(2)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(2).getStringCellValue()).thenReturn("Country");
        when(rowMock.getCell(3)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(3).getStringCellValue()).thenReturn("Operator");

        mccMncSheet.processRow(rowMock);
        assertEquals(1, mccMncSheet.validRows.size());

        MccMnc mccMnc = mccMncSheet.validRows.get(0);
        assertEquals(123, mccMnc.getMccId());
        assertEquals(456, mccMnc.getMncId());
        assertEquals("Country", mccMnc.getCountry());
        assertEquals("Operator", mccMnc.getOperator());
    }

    @Test
    void testSaveInBatches() {
        // Prepare data for batch saving
        MccMnc mccMnc1 = new MccMnc(123, 456, "Country1", "Operator1");
        MccMnc mccMnc2 = new MccMnc(789, 101, "Country2", "Operator2");
        List<MccMnc> mccMncList = Arrays.asList(mccMnc1, mccMnc2);
        mccMncSheet.validRows.addAll(mccMncList);

        mccMncSheet.saveInBatchs();
        verify(mccMncDAO).saveAllAndFlush(mccMncList);

        // Ensure validRows is cleared after saving
        assertEquals(0, mccMncSheet.validRows.size());
    }
}
