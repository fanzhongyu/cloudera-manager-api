package com.tima.cm.Bean;

public class Data {
    private String timestamp;

    private Double value;

    private String type;

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Double getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
