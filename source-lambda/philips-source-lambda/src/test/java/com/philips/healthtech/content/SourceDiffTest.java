package com.philips.healthtech.content;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SourceDiffTest {

    SourceDiff sourceDiff = new SourceDiff();

    @Test
    public void testGetIdOkWithoutPath() throws Exception {
        assertEquals("id", sourceDiff.getId("id.xml"));
    }

    @Test
    public void testGetIdOk() throws Exception {
        assertEquals("id", sourceDiff.getId("file/id.xml"));
    }

    @Test
    public void testGetIdOkSlashInFront() throws Exception {
        assertEquals("id", sourceDiff.getId("/file/id.xml"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIdEndSlash() throws Exception {
        sourceDiff.getId("/file/");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIdNoXml() throws Exception {
        sourceDiff.getId("/file/id");
    }

    @Test
    public void getKinesisPayloadCheckNew(){
        String payload = sourceDiff.getKinesisPayload("id", "{\"attr\": \"value two\"}", new SourceDelta(null, "{\"attr\": \"value two\"}"));
        assertEquals("{\"id\":\"id\",\"jsonString\":\"{\\\"attr\\\": \\\"value two\\\"}\",\"sourceDelta\":{\"oldDiff\":null,\"newDiff\":\"{\\\"attr\\\":\\\"value two\\\"}\",\"different\":true}}", payload);
    }
}