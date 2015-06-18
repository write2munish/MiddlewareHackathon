package com.philips.healthtech.content;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Record implements Serializable {
    private String eventID;
    private String eventName;
    private String eventVersion;
    private String eventSource;
    private String awsRegion;
    private StreamRecord dynamodb;

    public Record() {
    }

    public String getEventID() {
        return this.eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public Record withEventID(String eventID) {
        this.eventID = eventID;
        return this;
    }

    public String getEventName() {
        return this.eventName;
    }

    public Record withEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public void setEventName(OperationType eventName) {
        this.eventName = eventName.toString();
    }

    public Record withEventName(OperationType eventName) {
        this.eventName = eventName.toString();
        return this;
    }

    public String getEventVersion() {
        return this.eventVersion;
    }

    public void setEventVersion(String eventVersion) {
        this.eventVersion = eventVersion;
    }

    public Record withEventVersion(String eventVersion) {
        this.eventVersion = eventVersion;
        return this;
    }

    public String getEventSource() {
        return this.eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public Record withEventSource(String eventSource) {
        this.eventSource = eventSource;
        return this;
    }

    public String getAwsRegion() {
        return this.awsRegion;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public Record withAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
        return this;
    }

    public StreamRecord getDynamodb() {
        return this.dynamodb;
    }

    public void setDynamodb(StreamRecord dynamodb) {
        this.dynamodb = dynamodb;
    }

    public Record withDynamodb(StreamRecord dynamodb) {
        this.dynamodb = dynamodb;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if(this.getEventID() != null) {
            sb.append("EventID: " + this.getEventID() + ",");
        }

        if(this.getEventName() != null) {
            sb.append("EventName: " + this.getEventName() + ",");
        }

        if(this.getEventVersion() != null) {
            sb.append("EventVersion: " + this.getEventVersion() + ",");
        }

        if(this.getEventSource() != null) {
            sb.append("EventSource: " + this.getEventSource() + ",");
        }

        if(this.getAwsRegion() != null) {
            sb.append("AwsRegion: " + this.getAwsRegion() + ",");
        }

        if(this.getDynamodb() != null) {
            sb.append("Dynamodb: " + this.getDynamodb());
        }

        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        boolean prime = true;
        byte hashCode = 1;
        int hashCode1 = 31 * hashCode + (this.getEventID() == null?0:this.getEventID().hashCode());
        hashCode1 = 31 * hashCode1 + (this.getEventName() == null?0:this.getEventName().hashCode());
        hashCode1 = 31 * hashCode1 + (this.getEventVersion() == null?0:this.getEventVersion().hashCode());
        hashCode1 = 31 * hashCode1 + (this.getEventSource() == null?0:this.getEventSource().hashCode());
        hashCode1 = 31 * hashCode1 + (this.getAwsRegion() == null?0:this.getAwsRegion().hashCode());
        hashCode1 = 31 * hashCode1 + (this.getDynamodb() == null?0:this.getDynamodb().hashCode());
        return hashCode1;
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null) {
            return false;
        } else if(!(obj instanceof Record)) {
            return false;
        } else {
            Record other = (Record)obj;
            return other.getEventID() == null ^ this.getEventID() == null?false:(other.getEventID() != null && !other.getEventID().equals(this.getEventID())?false:(other.getEventName() == null ^ this.getEventName() == null?false:(other.getEventName() != null && !other.getEventName().equals(this.getEventName())?false:(other.getEventVersion() == null ^ this.getEventVersion() == null?false:(other.getEventVersion() != null && !other.getEventVersion().equals(this.getEventVersion())?false:(other.getEventSource() == null ^ this.getEventSource() == null?false:(other.getEventSource() != null && !other.getEventSource().equals(this.getEventSource())?false:(other.getAwsRegion() == null ^ this.getAwsRegion() == null?false:(other.getAwsRegion() != null && !other.getAwsRegion().equals(this.getAwsRegion())?false:(other.getDynamodb() == null ^ this.getDynamodb() == null?false:other.getDynamodb() == null || other.getDynamodb().equals(this.getDynamodb())))))))))));
        }
    }
}
