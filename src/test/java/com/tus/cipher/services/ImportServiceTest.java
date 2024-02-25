package com.tus.cipher.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tus.cipher.TestUtil;
import com.tus.cipher.services.sheets.BaseDataSheet;
import com.tus.cipher.services.sheets.BaseSheetProcessor;
import com.tus.cipher.services.sheets.EventCauseSheet;
import com.tus.cipher.services.sheets.FailureClassSheet;
import com.tus.cipher.services.sheets.MccMncSheet;
import com.tus.cipher.services.sheets.UeSheet;

class ImportServiceTest {

    private ImportService importService;

    private DataValidator dataValidatorMock;
    private BaseDataSheet baseDataSheetMock;
    private List<BaseSheetProcessor> refSheetsMock;

    private MccMncSheet mccMncSheetMock;
    private UeSheet ueSheetMock;
    private FailureClassSheet failureClassSheetMock;
    private EventCauseSheet eventCauseSheetMock;

    private ImportParams importParamsMock;
    @BeforeEach
    void setUp() {
        dataValidatorMock = mock(DataValidator.class);
        baseDataSheetMock = mock(BaseDataSheet.class);

        when(baseDataSheetMock.getSheetName()).thenReturn("Base Data");
        refSheetsMock = new ArrayList<>();

        // Reference Sheets
        mccMncSheetMock = mock(MccMncSheet.class);
        when(mccMncSheetMock.getSheetName()).thenReturn("MCC - MNC Table");
        refSheetsMock.add(mccMncSheetMock);

        ueSheetMock = mock(UeSheet.class);
        when(ueSheetMock.getSheetName()).thenReturn("UE Table");
        refSheetsMock.add(ueSheetMock);

        failureClassSheetMock = mock(FailureClassSheet.class);
        when(failureClassSheetMock.getSheetName()).thenReturn("Failure Class Table");
        refSheetsMock.add(failureClassSheetMock);

        eventCauseSheetMock = mock(EventCauseSheet.class);
        when(eventCauseSheetMock.getSheetName()).thenReturn("Event-Cause Table");
        refSheetsMock.add(eventCauseSheetMock);

        // Create a new instance of ImportService
        importParamsMock = mock(ImportParams.class);
        when(importParamsMock.getBaseDataSheet()).thenReturn(baseDataSheetMock);
        when(importParamsMock.getDataValidator()).thenReturn(dataValidatorMock);
        when(importParamsMock.getRefProcessors()).thenReturn(refSheetsMock);

        importService = new ImportService(importParamsMock);
    }

    @Test
    void testImportWorkBook() throws EncryptedDocumentException, IOException {
    	TestUtil.moveFileToDest("TUS_CallFailureData.xls", "call-failure-data");
        HSSFWorkbook workbook = (HSSFWorkbook) WorkbookFactory.create(new File("call-failure-data/" + "TUS_CallFailureData.xls"));

        // Call the method under test
        importService.importWorkBook(workbook);

        // Verify the Ref Sheets Imported
        verify(mccMncSheetMock).getSheetName();
        verify(mccMncSheetMock).saveInBatchs();

        verify(ueSheetMock).getSheetName();
        verify(ueSheetMock).saveInBatchs();

        verify(failureClassSheetMock).getSheetName();
        verify(failureClassSheetMock).saveInBatchs();

        verify(eventCauseSheetMock).getSheetName();
        verify(eventCauseSheetMock).saveInBatchs();

        // Verify Base Data Sheet Imported
        verify(dataValidatorMock).prepareValidator();
        verify(baseDataSheetMock).setValidator(dataValidatorMock);
        verify(baseDataSheetMock).getSheetName();
        verify(baseDataSheetMock).saveInBatchs();
    }
}
