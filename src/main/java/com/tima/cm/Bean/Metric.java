package com.tima.cm.Bean;

import java.util.ArrayList;
import java.util.List;

public class Metric {
    private String metricName;

    private String entityName;

    private String startTime;

    private String endTime;

    List<Data> data = new ArrayList<>();

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMetricName() {
        return metricName;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public List<Data> getData() {
        return data;
    }
}
