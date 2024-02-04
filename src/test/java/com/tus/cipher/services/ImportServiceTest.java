//package com.tus.cipher.services;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.inOrder;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.InOrder;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.tus.cipher.services.sheets.BaseDataSheet;
//import com.tus.cipher.services.sheets.BaseSheetProcessor;
//import com.tus.cipher.services.sheets.EventCauseSheet;
//import com.tus.cipher.services.sheets.FailureClassSheet;
//import com.tus.cipher.services.sheets.MccMncSheet;
//import com.tus.cipher.services.sheets.UeSheet;
//
//@ExtendWith(MockitoExtension.class)
//class ImportServiceTest {
//
//	@Mock
//    private MccMncSheet mccMncSheet;
//
//    @Mock
//    private UeSheet ueSheet;
//
//    @Mock
//    private FailureClassSheet failureClassSheet;
//
//    @Mock
//    private EventCauseSheet eventCauseSheet;
//
//    @Mock
//    private BaseDataSheet baseDataSheet;
//
//    @InjectMocks
//    private ImportService importService;
//
//    @Captor
//    private ArgumentCaptor<HSSFSheet> sheetCaptor;
//
//    private HSSFWorkbook mockWorkbook;
//    private HSSFSheet mockSheet;
//    private static final String FILE = "TUSGroupProject_SampleDataset.xls";
//
//    @BeforeEach
//    public void setUp() {
//        mockWorkbook = mock(HSSFWorkbook.class);
//        mockSheet = mock(HSSFSheet.class);
//    }
//
//    @Test
//    void testImportFile_Successful() throws IOException {
//
//        when(WorkbookFactory.create(new FileInputStream(FILE))).thenReturn(mockWorkbook);
//        when(mockWorkbook.getSheet(anyString())).thenReturn(mockSheet);
//        importService.importFile(FILE);
//
//        // Verify the order and content of sheets passed to importSheet method
//        InOrder inOrder = inOrder(mccMncSheet, ueSheet, failureClassSheet, eventCauseSheet, baseDataSheet);
//
//        inOrder.verify(importService).importSheet(BaseSheetProcessor.class, sheetCaptor.capture());
////
////        inOrder.verify(importUe).importSheet(any(), sheetCaptor.capture());
////        inOrder.verify(importFailureClass).importSheet(any(), sheetCaptor.capture());
////        inOrder.verify(importEventCause).importSheet(any(), sheetCaptor.capture());
////        inOrder.verify(importBaseData).importSheet(any(), sheetCaptor.capture());
//
//        // Assert the order and content of the sheets
//        assertEquals("MccMncSheet", sheetCaptor.getAllValues().get(0).getSheetName());
////        assertEquals("UeSheet", sheetCaptor.getAllValues().get(1).getSheetName());
////        assertEquals("FailureClassSheet", sheetCaptor.getAllValues().get(2).getSheetName());
////        assertEquals("EventCauseSheet", sheetCaptor.getAllValues().get(3).getSheetName());
////        assertEquals("BaseDataSheet", sheetCaptor.getAllValues().get(4).getSheetName());
//
//    }
//
//    @Test
//    void testImportFile_IOException() throws IOException {
//        // Write your test case for IOException handling
//    }
//
//    @Test
//    void testImportSheet_BatchProcessing() {
//        // Write your test case for batch processing of entities
//    }
//
//    @Test
//    void testValidationService_Setting() {
//        // Write your test case for validation service setting
//    }
//}
