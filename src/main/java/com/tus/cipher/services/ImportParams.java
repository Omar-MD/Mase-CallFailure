package com.tus.cipher.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

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

@Component
public class ImportParams {

	private CallFailureDAO callFailureDAO;
	private MccMncDAO mccMncDAO;
	private UeDAO ueDAO;
	private FailureClassDAO failureClassDAO;
	private EventCauseDAO eventCauseDAO;

	public ImportParams(
            CallFailureDAO callFailureDAO,
            MccMncDAO mccMncDAO,
            UeDAO ueDAO,
            FailureClassDAO failureClassDAO,
            EventCauseDAO eventCauseDAO
    ) {
        this.callFailureDAO = callFailureDAO;
        this.mccMncDAO = mccMncDAO;
        this.ueDAO = ueDAO;
        this.failureClassDAO = failureClassDAO;
        this.eventCauseDAO = eventCauseDAO;
    }

	public CallFailureDAO getCallFailureDAO() {
		return callFailureDAO;
	}

	public MccMncDAO getMccMncDAO() {
		return mccMncDAO;
	}

	public UeDAO getUeDAO() {
		return ueDAO;
	}

	public FailureClassDAO getFailureClassDAO() {
		return failureClassDAO;
	}

	public EventCauseDAO getEventCauseDAO() {
		return eventCauseDAO;
	}

	public BaseDataSheet getBaseDataSheet() {
		return new BaseDataSheet(callFailureDAO);
	}

	public List<BaseSheetProcessor> getRefProcessors(){
		List<BaseSheetProcessor> refProc = new ArrayList<>();
		refProc.add(new MccMncSheet(mccMncDAO));
		refProc.add(new UeSheet(ueDAO));
		refProc.add(new FailureClassSheet(failureClassDAO));
		refProc.add(new EventCauseSheet(eventCauseDAO));
		return refProc;
	}

	public DataValidator getDataValidator() {
		return new DataValidator(mccMncDAO, ueDAO, failureClassDAO, eventCauseDAO);
	}
}
