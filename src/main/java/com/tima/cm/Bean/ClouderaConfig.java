package com.tima.cm.Bean;

public class ClouderaConfig {
    private String clouderaUrl;

    private String clouderaPort;

    private String clouderaUserName;

    private String clouderaPassword;

    public void setClouderaUrl(String clouderaUrl) {
        this.clouderaUrl = clouderaUrl;
    }

    public void setClouderaPort(String clouderaPort) {
        this.clouderaPort = clouderaPort;
    }

    public void setClouderaUserName(String clouderaUserName) {
        this.clouderaUserName = clouderaUserName;
    }

    public void setClouderaPassword(String clouderaPassword) {
        this.clouderaPassword = clouderaPassword;
    }

    public String getClouderaUrl() {
        return clouderaUrl;
    }

    public String getClouderaPort() {
        return clouderaPort;
    }

    public String getClouderaUserName() {
        return clouderaUserName;
    }

    public String getClouderaPassword() {
        return clouderaPassword;
    }
}
