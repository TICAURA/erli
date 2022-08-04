package com.aura.qamm.dto;

import java.io.Serializable;
import java.util.List;

public class Emails implements Serializable {

    private static final long serialVersionUID = 2190444410611010816L;

    private String subject;
    private List<String> to;
    private String from;
    private String body;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String emailSubject) {
        this.subject = emailSubject;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        String emailJson = "{\"email\":{" +
                "\"text\":\"" + body + "\"" +
                ",\"subject\":\"" + subject + "\"" +

                ",\"from\":{  \"email\":\"" + from + "\"}" +
                ",\"to\":[";

        for(int i = 0; i<to.size(); i++){
            emailJson+= "{  \"email\":\"" + to.get(i) + "\"}";
            if(i+1<to.size()){emailJson+= ",";}
        }
        emailJson+=   "]}}";
        return emailJson;
    }
}

