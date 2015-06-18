package com.philips.healthtech.content;

public enum OperationType {
    INSERT("INSERT"),
    MODIFY("MODIFY"),
    REMOVE("REMOVE");

    private String value;

    private OperationType(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static OperationType fromValue(String value) {
        if(value != null && !"".equals(value)) {
            if("INSERT".equals(value)) {
                return INSERT;
            } else if("MODIFY".equals(value)) {
                return MODIFY;
            } else if("REMOVE".equals(value)) {
                return REMOVE;
            } else {
                throw new IllegalArgumentException("Cannot create enum from " + value + " value!");
            }
        } else {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
    }
}
