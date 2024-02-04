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

import com.tus.cipher.dao.EventCauseDAO;
import com.tus.cipher.dto.sheets.EventCause;

class EventCauseSheetTest {

    private EventCauseSheet eventCauseSheet;
    private EventCauseDAO eventCauseDAO;
    private Row rowMock;

    @BeforeEach
    public void setUp() {
        eventCauseDAO = mock(EventCauseDAO.class);
        eventCauseSheet = new EventCauseSheet(eventCauseDAO);
        rowMock = mock(Row.class);
    }

    @Test
    void testSheetNameAndBatchSize() {
        assertEquals("Event-Cause Table", eventCauseSheet.getSheetName());
        assertEquals(128, eventCauseSheet.getBatchSize());
    }

    @Test
    void testProcessRow_ValidRow() {
    	when(rowMock.getCell(0)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(0).getNumericCellValue()).thenReturn(123.0);
        when(rowMock.getCell(1)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(1).getNumericCellValue()).thenReturn(456.0);
        when(rowMock.getCell(2)).thenReturn(mock(Cell.class));
        when(rowMock.getCell(2).getStringCellValue()).thenReturn("Description");

        eventCauseSheet.processRow(rowMock);
        assertEquals(1, eventCauseSheet.validRows.size());

        EventCause eventCause = eventCauseSheet.validRows.get(0);
        assertEquals(123, eventCause.getCauseCode());
        assertEquals(456, eventCause.getEventId());
        assertEquals("Description", eventCause.getDescription());
    }

    @Test
    void testSaveInBatches() {
        // Prepare data for batch saving
        EventCause eventCause1 = new EventCause(123, 456, "Description1");
        EventCause eventCause2 = new EventCause(789, 101, "Description2");
        List<EventCause> eventCauseList = Arrays.asList(eventCause1, eventCause2);
        eventCauseSheet.validRows.addAll(eventCauseList);

        eventCauseSheet.saveInBatchs();
        verify(eventCauseDAO).saveAllAndFlush(eventCauseList);

        // Ensure validRows is cleared after saving
        assertEquals(0, eventCauseSheet.validRows.size());
    }
}
