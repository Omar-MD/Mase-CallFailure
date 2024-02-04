//package com.tus.cipher.services;
//
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import com.tus.cipher.services.sheets.BaseSheetProcessor;
//
//class ImportServiceTest {
//
//    private ImportService importService;
//    private DataValidator dataValidatorMock;
//    private BaseSheetProcessor baseSheetProcessorMock;
//    private List<BaseSheetProcessor> refProcessorsMock;
//
//    @BeforeEach
//    void setUp() {
//        dataValidatorMock = mock(DataValidator.class);
//        baseSheetProcessorMock = mock(BaseSheetProcessor.class);
//        refProcessorsMock = new ArrayList<>();
//        refProcessorsMock.add(mock(BaseSheetProcessor.class)); // Add a mock processor
//
//        importService = new ImportService(refProcessorsMock, dataValidatorMock, baseSheetProcessorMock);
//    }
//
//    @Test
//    void testImportWorkBook() {
//        HSSFWorkbook workbookMock = mock(HSSFWorkbook.class);
//
//        doNothing().when(importService).importReferenceSheets(workbookMock);
//        doNothing().when(importService).importBaseData(workbookMock);
//
//        importService.importWorkBook(workbookMock);
//
//        verify(importService).importReferenceSheets(workbookMock);
//        verify(importService).importBaseData(workbookMock);
//    }
//
////    @Test
////    void testImportReferenceSheets() {
////        HSSFWorkbook workbookMock = mock(HSSFWorkbook.class);
////        HSSFSheet sheetMock = mock(HSSFSheet.class);
////
////        when(baseSheetProcessorMock.getSheetName()).thenReturn("SheetName");
////
////        importService.importReferenceSheets(workbookMock);
////
////        // Verify that importSheet was called for each processor
////        for (BaseSheetProcessor processor : refProcessorsMock) {
////            verify(importService).importSheet(processor, sheetMock);
////        }
////    }
////
////    @Test
////    void testImportBaseData() {
////        HSSFWorkbook workbookMock = mock(HSSFWorkbook.class);
////        HSSFSheet sheetMock = mock(HSSFSheet.class);
////
////        // Mock the behavior of the base sheet processor
////        when(baseSheetProcessorMock.getSheetName()).thenReturn("SheetName");
////
////        // Test the importBaseData method
////        importService.importBaseData(workbookMock);
////
////        // Verify that importSheet was called with the correct processor and sheet
////        verify(importService).importSheet(baseSheetProcessorMock, sheetMock);
////    }
//}
