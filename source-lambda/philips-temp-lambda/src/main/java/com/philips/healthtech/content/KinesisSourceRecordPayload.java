package com.philips.healthtech.content;

public class KinesisSourceRecordPayload {
    private String id;
    private String jsonString;
    private SourceDelta sourceDelta;

    public KinesisSourceRecordPayload() {
    }

    public KinesisSourceRecordPayload(String id, String jsonString, SourceDelta sourceDelta) {
        this.id = id;
        this.jsonString = jsonString;
        this.sourceDelta = sourceDelta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public SourceDelta getSourceDelta() {
        return sourceDelta;
    }

    public void setSourceDelta(SourceDelta sourceDelta) {
        this.sourceDelta = sourceDelta;
    }
}
