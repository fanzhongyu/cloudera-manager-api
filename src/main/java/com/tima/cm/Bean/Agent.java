package com.tima.cm.Bean;

import com.cloudera.api.model.ApiHealthSummary;

public class Agent {
    private String name;

    private ApiHealthSummary status;

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(ApiHealthSummary status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public ApiHealthSummary getStatus() {
        return status;
    }
}
