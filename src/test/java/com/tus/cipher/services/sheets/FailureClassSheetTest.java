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

import com.tus.cipher.dao.FailureClassDAO;
import com.tus.cipher.dto.sheets.FailureClass;

class FailureClassSheetTest {

    private FailureClassSheet failureClassSheet;
    private FailureClassDAO failureClassDAO;
    private Row rowMock;

    @BeforeEach
    public void setUp() {
        failureClassDAO = mock(FailureClassDAO.class);
        failureClassSheet = new FailureClassSheet(failureClassDAO);
        rowMock = mock(Row.class);
    }

    @Test
    void testSheetNameAndBatchSize() {
        assertEquals("Failure Class Table", failureClassSheet.getSheetName());
        assertEquals(128, failureClassSheet.getBatchSize());
    }

    @Test
    void testProcessRow_ValidRow() {
    	when(rowMock.getCell(0)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(0).getNumericCellValue()).thenReturn(123.0);
        when(rowMock.getCell(1)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(1).getStringCellValue()).thenReturn("Description");

        failureClassSheet.processRow(rowMock);
        assertEquals(1, failureClassSheet.validRows.size());

        FailureClass failureClass = failureClassSheet.validRows.get(0);
        assertEquals(123, failureClass.getFailure());
        assertEquals("Description", failureClass.getDescription());
    }

    @Test
    void testSaveInBatches() {
        // Prepare data for batch saving
        FailureClass failureClass1 = new FailureClass(123, "Description1");
        FailureClass failureClass2 = new FailureClass(456, "Description2");
        List<FailureClass> failureClassList = Arrays.asList(failureClass1, failureClass2);
        failureClassSheet.validRows.addAll(failureClassList);

        failureClassSheet.saveInBatchs();
        verify(failureClassDAO).saveAllAndFlush(failureClassList);

        // Ensure validRows is cleared after saving
        assertEquals(0, failureClassSheet.validRows.size());
    }
}
