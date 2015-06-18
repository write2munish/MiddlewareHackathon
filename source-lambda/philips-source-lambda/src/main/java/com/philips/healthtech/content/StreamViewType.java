package com.philips.healthtech.content;

public enum StreamViewType {
    NEW_IMAGE("NEW_IMAGE"),
    OLD_IMAGE("OLD_IMAGE"),
    NEW_AND_OLD_IMAGES("NEW_AND_OLD_IMAGES"),
    KEYS_ONLY("KEYS_ONLY");

    private String value;

    private StreamViewType(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static StreamViewType fromValue(String value) {
        if(value != null && !"".equals(value)) {
            if("NEW_IMAGE".equals(value)) {
                return NEW_IMAGE;
            } else if("OLD_IMAGE".equals(value)) {
                return OLD_IMAGE;
            } else if("NEW_AND_OLD_IMAGES".equals(value)) {
                return NEW_AND_OLD_IMAGES;
            } else if("KEYS_ONLY".equals(value)) {
                return KEYS_ONLY;
            } else {
                throw new IllegalArgumentException("Cannot create enum from " + value + " value!");
            }
        } else {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
    }
}
