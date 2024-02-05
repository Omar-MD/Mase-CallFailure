package com.tus.cipher.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.tus.cipher.dto.sheets.CallFailure;
import com.tus.cipher.dto.sheets.EventCause;
import com.tus.cipher.dto.sheets.FailureClass;
import com.tus.cipher.dto.sheets.MccMnc;
import com.tus.cipher.dto.sheets.Ue;

class SheetDTOTest {

    @Test
    void testCallFailureDTO() {
        CallFailure callFailure = new CallFailure();
        LocalDateTime dateTime = LocalDateTime.now();
        callFailure.setDateTime(dateTime);
        callFailure.setEventId(1);
        callFailure.setCauseCode(2);
        callFailure.setFailureCode(3);
        callFailure.setDuration(4);
        callFailure.setCellId(5);
        callFailure.setTac(6L);
        callFailure.setMcc(7);
        callFailure.setMnc(8);
        callFailure.setNeVersion("NE Version");
        callFailure.setImsi(9L);
        callFailure.setHier3Id(10L);
        callFailure.setHier32Id(11L);
        callFailure.setHier321Id(12L);

        assertEquals(dateTime, callFailure.getDateTime());
        assertEquals(1, callFailure.getEventId());
        assertEquals(2, callFailure.getCauseCode());
        assertEquals(3, callFailure.getFailureCode());
        assertEquals(4, callFailure.getDuration());
        assertEquals(5, callFailure.getCellId());
        assertEquals(6L, callFailure.getTac());
        assertEquals(7, callFailure.getMcc());
        assertEquals(8, callFailure.getMnc());
        assertEquals("NE Version", callFailure.getNeVersion());
        assertEquals(9L, callFailure.getImsi());
        assertEquals(10L, callFailure.getHier3Id());
        assertEquals(11L, callFailure.getHier32Id());
        assertEquals(12L, callFailure.getHier321Id());
    }

    @Test
    void testEventCauseDTO() {
        EventCause eventCause = new EventCause();
        eventCause.setCauseCode(1);
        eventCause.setEventId(2);
        eventCause.setDescription("Description");

        assertEquals(1, eventCause.getCauseCode());
        assertEquals(2, eventCause.getEventId());
        assertEquals("Description", eventCause.getDescription());
    }

    @Test
    void testFailureClassDTO() {
        FailureClass failureClass = new FailureClass();
        failureClass.setFailure(1);
        failureClass.setDescription("Description");

        assertEquals(1, failureClass.getFailure());
        assertEquals("Description", failureClass.getDescription());
    }

    @Test
    void testMccMncDTO() {
        MccMnc mccMnc = new MccMnc();
        mccMnc.setMccId(123);
        mccMnc.setMncId(456);
        mccMnc.setCountry("Country");
        mccMnc.setOperator("Operator");

        assertEquals(123, mccMnc.getMccId());
        assertEquals(456, mccMnc.getMncId());
        assertEquals("Country", mccMnc.getCountry());
        assertEquals("Operator", mccMnc.getOperator());
    }

    @Test
    void testUeDTO() {
        Ue ue = new Ue();
        ue.setTac(123L);
        ue.setMarketingName("MarketingName");
        ue.setManufacturer("Manufacturer");
        ue.setAccessCapability("AccessCapability");

        assertEquals(123L, ue.getTac());
        assertEquals("MarketingName", ue.getMarketingName());
        assertEquals("Manufacturer", ue.getManufacturer());
        assertEquals("AccessCapability", ue.getAccessCapability());
    }
}
