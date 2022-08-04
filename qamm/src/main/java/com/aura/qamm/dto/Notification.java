package com.aura.qamm.dto;

public class Notification {
    private long id_notification;
    private String title;
    private String contents;
    private String url;
    private String uuid;
    private boolean seen;


    public Notification() {

    }

    public long getId_notification() {
        return id_notification;
    }

    public void setId_notification(long id_notification) {
        this.id_notification = id_notification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }


}
