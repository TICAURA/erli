package com.aura.qamm.model;

public class MailServer {
    private String host;
    private String from;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "MailServer{" +
                "host='" + host + '\'' +
                ", from='" + from + '\'' +
                '}';
    }

    public MailServer(String host, String from) {
        this.host = host;
        this.from = from;
    }

    public MailServer() {
    }
}
