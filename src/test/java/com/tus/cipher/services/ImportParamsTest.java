package com.tus.cipher.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.dao.EventCauseDAO;
import com.tus.cipher.dao.FailureClassDAO;
import com.tus.cipher.dao.MccMncDAO;
import com.tus.cipher.dao.UeDAO;
import com.tus.cipher.services.sheets.BaseDataSheet;
import com.tus.cipher.services.sheets.BaseSheetProcessor;
import com.tus.cipher.services.sheets.EventCauseSheet;
import com.tus.cipher.services.sheets.FailureClassSheet;
import com.tus.cipher.services.sheets.MccMncSheet;
import com.tus.cipher.services.sheets.UeSheet;

class ImportParamsTest {

    private CallFailureDAO callFailureDAO;
    private MccMncDAO mccMncDAO;
    private UeDAO ueDAO;
    private FailureClassDAO failureClassDAO;
    private EventCauseDAO eventCauseDAO;
    private ImportParams importParams;

    @BeforeEach
    void setUp() {
        callFailureDAO = mock(CallFailureDAO.class);
        mccMncDAO = mock(MccMncDAO.class);
        ueDAO = mock(UeDAO.class);
        failureClassDAO = mock(FailureClassDAO.class);
        eventCauseDAO = mock(EventCauseDAO.class);
        importParams = new ImportParams(callFailureDAO, mccMncDAO, ueDAO, failureClassDAO, eventCauseDAO);
    }

    @Test
    void testGetBaseDataSheet() {
        BaseDataSheet baseDataSheet = importParams.getBaseDataSheet();
        assertEquals(BaseDataSheet.class, baseDataSheet.getClass());
    }

    @Test
    void testGetRefProcessors() {
        List<BaseSheetProcessor> refProcessors = importParams.getRefProcessors();
        assertEquals(4, refProcessors.size());
        assertEquals(MccMncSheet.class, refProcessors.get(0).getClass());
        assertEquals(UeSheet.class, refProcessors.get(1).getClass());
        assertEquals(FailureClassSheet.class, refProcessors.get(2).getClass());
        assertEquals(EventCauseSheet.class, refProcessors.get(3).getClass());
    }

    @Test
    void testGetDataValidator() {
        DataValidator dataValidator = importParams.getDataValidator();
        assertEquals(DataValidator.class, dataValidator.getClass());
    }
}
