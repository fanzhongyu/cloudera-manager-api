package com.tima.cm.Bean;

import java.util.ArrayList;
import java.util.List;

public class Host {
    private String hostId;

    private String hostName;

    private String ipAddress;

    private String rack;

    private String lastHeart;

    private List<String> services = new ArrayList<>();

    private long numCores;

    private long numPhysicalCores;

    private long totalPhysMemBytes;

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public void setLastHeart(String lastHeart) {
        this.lastHeart = lastHeart;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public void setNumCores(long numCores) {
        this.numCores = numCores;
    }

    public void setNumPhysicalCores(long numPhysicalCores) {
        this.numPhysicalCores = numPhysicalCores;
    }

    public void setTotalPhysMemBytes(long totalPhysMemBytes) {
        this.totalPhysMemBytes = totalPhysMemBytes;
    }

    public String getHostId() {
        return hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getRack() {
        return rack;
    }

    public String getLastHeart() {
        return lastHeart;
    }

    public List<String> getServices() {
        return services;
    }

    public long getNumCores() {
        return numCores;
    }

    public long getNumPhysicalCores() {
        return numPhysicalCores;
    }

    public long getTotalPhysMemBytes() {
        return totalPhysMemBytes;
    }
}
