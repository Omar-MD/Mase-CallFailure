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

import com.tus.cipher.dao.UeDAO;
import com.tus.cipher.dto.sheets.Ue;

class UeSheetTest {

    private UeSheet ueSheet;
    private UeDAO ueDAO;
    private Row rowMock;

    @BeforeEach
    public void setUp() {
        ueDAO = mock(UeDAO.class);
        ueSheet = new UeSheet(ueDAO);
        rowMock = mock(Row.class);
    }

    @Test
    void testSheetNameAndBatchSize() {
        assertEquals("UE Table", ueSheet.getSheetName());
        assertEquals(128, ueSheet.getBatchSize());
    }

    @Test
    void testProcessRow_ValidRow() {
    	when(rowMock.getCell(0)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(0).getNumericCellValue()).thenReturn(123.0);
        when(rowMock.getCell(1)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(1).getStringCellValue()).thenReturn("MarketingName");
        when(rowMock.getCell(2)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(2).getStringCellValue()).thenReturn("Manufacturer");
        when(rowMock.getCell(3)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(3).getStringCellValue()).thenReturn("AccessCapability");

        ueSheet.processRow(rowMock);
        assertEquals(1, ueSheet.validRows.size());

        Ue ue = ueSheet.validRows.get(0);
        assertEquals(123, ue.getTac());
        assertEquals("MarketingName", ue.getMarketingName());
        assertEquals("Manufacturer", ue.getManufacturer());
        assertEquals("AccessCapability", ue.getAccessCapability());
    }

    @Test
    void testSaveInBatches() {
        // Prepare data for batch saving
        Ue ue1 = new Ue(123L, "MarketingName1", "Manufacturer1", "AccessCapability1");
        Ue ue2 = new Ue(456L, "MarketingName2", "Manufacturer2", "AccessCapability2");
        List<Ue> ueList = Arrays.asList(ue1, ue2);
        ueSheet.validRows.addAll(ueList);

        ueSheet.saveInBatchs();
        verify(ueDAO).saveAllAndFlush(ueList);

        // Ensure validRows is cleared after saving
        assertEquals(0, ueSheet.validRows.size());
    }
}
