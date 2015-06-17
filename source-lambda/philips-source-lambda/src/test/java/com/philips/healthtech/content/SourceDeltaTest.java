package com.philips.healthtech.content;

import org.junit.Test;

import static org.junit.Assert.*;

public class SourceDeltaTest {

    @Test
    public void nullCheck(){
        SourceDelta sourceDelta = new SourceDelta(null, null);
        assertFalse(sourceDelta.isDifferent());
        assertNull(sourceDelta.getOldDiff());
        assertNull(sourceDelta.getNewDiff());
    }

    @Test
    public void emptyCheck(){
        SourceDelta sourceDelta = new SourceDelta("{}", "{}");
        assertFalse(sourceDelta.isDifferent());
        assertNull(sourceDelta.getOldDiff());
        assertNull(sourceDelta.getNewDiff());
    }

    @Test
    public void oldOnlyCheck(){
        SourceDelta sourceDelta = new SourceDelta("{\"attr\": \"value one\"}", "{}");
        assertTrue(sourceDelta.isDifferent());
        assertEquals("{\"attr\":\"value one\"}", sourceDelta.getOldDiff());
        assertEquals("{}", sourceDelta.getNewDiff());
    }

    @Test
    public void oldOnlyNewNullCheck(){
        SourceDelta sourceDelta = new SourceDelta("{\"attr\": \"value one\"}", null);
        assertTrue(sourceDelta.isDifferent());
        assertEquals("{\"attr\":\"value one\"}", sourceDelta.getOldDiff());
        assertNull(sourceDelta.getNewDiff());
    }

    @Test
    public void newOnlyCheck(){
        SourceDelta sourceDelta = new SourceDelta("{}", "{\"attr\": \"value two\"}");
        assertTrue(sourceDelta.isDifferent());
        assertEquals("{}", sourceDelta.getOldDiff());
        assertEquals("{\"attr\":\"value two\"}", sourceDelta.getNewDiff());
    }

    @Test
    public void newOnlyOldNullCheck(){
        SourceDelta sourceDelta = new SourceDelta(null, "{\"attr\": \"value two\"}");
        assertTrue(sourceDelta.isDifferent());
        assertNull(sourceDelta.getOldDiff());
        assertEquals("{\"attr\":\"value two\"}", sourceDelta.getNewDiff());
    }

    @Test
    public void bothSameCheck(){
        SourceDelta sourceDelta = new SourceDelta("{\"attr\": \"value two\"}", "{\"attr\": \"value two\"}");
        assertFalse(sourceDelta.isDifferent());
        assertNull(sourceDelta.getOldDiff());
        assertNull(sourceDelta.getNewDiff());
    }
}