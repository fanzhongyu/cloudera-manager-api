package com.tima.cm.Bean;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private String name;

    private String type;

    private List<Agent> agentList = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAgentList(List<Agent> agentList) {
        this.agentList = agentList;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<Agent> getAgentList() {
        return agentList;
    }
}
