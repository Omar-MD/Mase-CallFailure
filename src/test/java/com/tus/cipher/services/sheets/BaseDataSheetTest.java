package com.tus.cipher.services.sheets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.dto.sheets.CallFailure;
import com.tus.cipher.services.DataValidator;
import com.tus.cipher.services.LoggerService;

class BaseDataSheetTest {

    private BaseDataSheet baseDataSheet;
    private CallFailureDAO callFailureDAO;
    private DataValidator validator;
    private Row rowMock;
    private LoggerService logger = LoggerService.INSTANCE;

    @BeforeEach
    public void setUp() {
    	callFailureDAO = mock(CallFailureDAO.class);
    	validator = mock(DataValidator.class);
        baseDataSheet = new BaseDataSheet(callFailureDAO);
        baseDataSheet.setValidator(validator);
        logger.setLogFilePath("logs/test_log.txt");
        rowMock = mock(Row.class);
    }

    @Test
    void testSheetNameAndBatchSize() {
        assertEquals("Base Data", baseDataSheet.getSheetName());
        assertEquals(5120, baseDataSheet.getBatchSize());
    }

    @Test
    void testProcessRow_ValidRow() {
        when(rowMock.getCell(ArgumentMatchers.anyInt())).thenReturn(mock(Cell.class));

        LocalDateTime dateTime = LocalDateTime.now();
        when(rowMock.getCell(0).getLocalDateTimeCellValue()).thenReturn(dateTime);
        when(rowMock.getCell(1).getNumericCellValue()).thenReturn(1.0);
        when(rowMock.getCell(2).getNumericCellValue()).thenReturn(1.0);
        when(rowMock.getCell(3).getNumericCellValue()).thenReturn(1.0);

        when(rowMock.getCell(4).getNumericCellValue()).thenReturn(1.0);
        when(rowMock.getCell(5).getNumericCellValue()).thenReturn(1.0);
        when(rowMock.getCell(6).getNumericCellValue()).thenReturn(1.0);

        when(rowMock.getCell(7).getNumericCellValue()).thenReturn(1.0);
        when(rowMock.getCell(8).getNumericCellValue()).thenReturn(1.0);

        when(rowMock.getCell(9).getStringCellValue()).thenReturn("11B");

        when(rowMock.getCell(10).getNumericCellValue()).thenReturn(1.0);
        when(rowMock.getCell(11).getNumericCellValue()).thenReturn(1.0);
        when(rowMock.getCell(12).getNumericCellValue()).thenReturn(1.0);
        when(rowMock.getCell(13).getNumericCellValue()).thenReturn(1.0);

        baseDataSheet.processRow(rowMock);

        verify(validator).validate(ArgumentMatchers.any(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt(),
        		ArgumentMatchers.anyInt(), ArgumentMatchers.anyString(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());

        assertEquals(1, baseDataSheet.validRows.size());
    }

    @Test
    void testProcessRow_InvalidRow() {

        when(rowMock.getCell(ArgumentMatchers.anyInt())).thenReturn(mock(Cell.class));

        // Simulate exception to represent an invalid row
        when(rowMock.getCell(0).getLocalDateTimeCellValue()).thenThrow(new RuntimeException("Invalid cell value"));


        baseDataSheet.processRow(rowMock);

        verify(validator, times(0)).validate(ArgumentMatchers.any(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt(),
        		ArgumentMatchers.anyInt(), ArgumentMatchers.anyString(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());

        assertEquals(0, baseDataSheet.validRows.size());
    }

    @Test
    void testSaveInBatches() {
        CallFailure cf1 = new CallFailure();
        CallFailure cf2 = new CallFailure();
        List<CallFailure> callFailures = Arrays.asList(cf1, cf2);
        baseDataSheet.validRows.addAll(callFailures);

        baseDataSheet.saveInBatchs();
        verify(callFailureDAO).saveAll(callFailures);
    }
}