package com.philips.healthtech.content;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.databind.JsonNode;

public class StreamRecord implements Serializable {
    private JsonNode keys;
    private JsonNode newImage;
    private JsonNode oldImage;
    private String sequenceNumber;
    private Long sizeBytes;
    private String streamViewType;

    public StreamRecord() {
    }

    public JsonNode getKeys() {
        return this.keys;
    }

    public void setKeys(JsonNode keys) {
        this.keys = keys;
    }

    public StreamRecord clearKeysEntries() {
        this.keys = null;
        return this;
    }

    public JsonNode getNewImage() {
        return this.newImage;
    }

    public void setNewImage(JsonNode newImage) {
        this.newImage = newImage;
    }

    public StreamRecord clearNewImageEntries() {
        this.newImage = null;
        return this;
    }

    public JsonNode getOldImage() {
        return this.oldImage;
    }

    public void setOldImage(JsonNode oldImage) {
        this.oldImage = oldImage;
    }

    public StreamRecord clearOldImageEntries() {
        this.oldImage = null;
        return this;
    }

    public String getSequenceNumber() {
        return this.sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public StreamRecord withSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    public Long getSizeBytes() {
        return this.sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public StreamRecord withSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
        return this;
    }

    public String getStreamViewType() {
        return this.streamViewType;
    }

    public StreamRecord withStreamViewType(String streamViewType) {
        this.streamViewType = streamViewType;
        return this;
    }

    public void setStreamViewType(StreamViewType streamViewType) {
        this.streamViewType = streamViewType.toString();
    }

    public StreamRecord withStreamViewType(StreamViewType streamViewType) {
        this.streamViewType = streamViewType.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if(this.getKeys() != null) {
            sb.append("Keys: " + this.getKeys() + ",");
        }

        if(this.getNewImage() != null) {
            sb.append("NewImage: " + this.getNewImage() + ",");
        }

        if(this.getOldImage() != null) {
            sb.append("OldImage: " + this.getOldImage() + ",");
        }

        if(this.getSequenceNumber() != null) {
            sb.append("SequenceNumber: " + this.getSequenceNumber() + ",");
        }

        if(this.getSizeBytes() != null) {
            sb.append("SizeBytes: " + this.getSizeBytes() + ",");
        }

        if(this.getStreamViewType() != null) {
            sb.append("StreamViewType: " + this.getStreamViewType());
        }

        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        boolean prime = true;
        byte hashCode = 1;
        int hashCode1 = 31 * hashCode + (this.getKeys() == null?0:this.getKeys().hashCode());
        hashCode1 = 31 * hashCode1 + (this.getNewImage() == null?0:this.getNewImage().hashCode());
        hashCode1 = 31 * hashCode1 + (this.getOldImage() == null?0:this.getOldImage().hashCode());
        hashCode1 = 31 * hashCode1 + (this.getSequenceNumber() == null?0:this.getSequenceNumber().hashCode());
        hashCode1 = 31 * hashCode1 + (this.getSizeBytes() == null?0:this.getSizeBytes().hashCode());
        hashCode1 = 31 * hashCode1 + (this.getStreamViewType() == null?0:this.getStreamViewType().hashCode());
        return hashCode1;
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null) {
            return false;
        } else if(!(obj instanceof StreamRecord)) {
            return false;
        } else {
            StreamRecord other = (StreamRecord)obj;
            return other.getKeys() == null ^ this.getKeys() == null?false:(other.getKeys() != null && !other.getKeys().equals(this.getKeys())?false:(other.getNewImage() == null ^ this.getNewImage() == null?false:(other.getNewImage() != null && !other.getNewImage().equals(this.getNewImage())?false:(other.getOldImage() == null ^ this.getOldImage() == null?false:(other.getOldImage() != null && !other.getOldImage().equals(this.getOldImage())?false:(other.getSequenceNumber() == null ^ this.getSequenceNumber() == null?false:(other.getSequenceNumber() != null && !other.getSequenceNumber().equals(this.getSequenceNumber())?false:(other.getSizeBytes() == null ^ this.getSizeBytes() == null?false:(other.getSizeBytes() != null && !other.getSizeBytes().equals(this.getSizeBytes())?false:(other.getStreamViewType() == null ^ this.getStreamViewType() == null?false:other.getStreamViewType() == null || other.getStreamViewType().equals(this.getStreamViewType())))))))))));
        }
    }
}
