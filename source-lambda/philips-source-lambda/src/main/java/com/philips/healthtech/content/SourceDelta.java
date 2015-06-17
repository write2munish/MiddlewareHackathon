package com.philips.healthtech.content;

import com.bazaarvoice.jolt.Diffy;
import com.bazaarvoice.jolt.JsonUtils;

public class SourceDelta {
    private String oldDiff;
    private String newDiff;
    private boolean different;

    public SourceDelta() {
    }

    public SourceDelta(String oldJson, String newJson) {
        String oldProcessedJson = oldJson == null ? "{}" : oldJson;
        String newProcessedJson = newJson == null ? "{}" : newJson;

        Diffy diffy = new Diffy();
        Diffy.Result diff = diffy.diff(JsonUtils.jsonToMap(oldProcessedJson), JsonUtils.jsonToMap(newProcessedJson));
        different = !diff.isEmpty();
        if(different){
            if(oldJson != null) {
                oldDiff = JsonUtils.toJsonString(diff.expected);
            } else {
                oldDiff = null;
            }

            if(newJson != null) {
                newDiff = JsonUtils.toJsonString(diff.actual);
            } else {
                newDiff = null;
            }
        }

    }

    public String getOldDiff() {
        return oldDiff;
    }

    public void setOldDiff(String oldDiff) {
        this.oldDiff = oldDiff;
    }

    public String getNewDiff() {
        return newDiff;
    }

    public void setNewDiff(String newDiff) {
        this.newDiff = newDiff;
    }

    public boolean isDifferent() {
        return different;
    }

    public void setDifferent(boolean different) {
        this.different = different;
    }
}
