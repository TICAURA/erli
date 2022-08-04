package com.aura.qamm.dto;

import java.io.Serializable;

public class Email implements Serializable {

    private static final long serialVersionUID = 2190444410611010816L;

    private String subject;
    private Integer template;
    private String to;
    private String from;
    private String body;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String emailSubject) {
        this.subject = emailSubject;
    }

    public Integer getTemplate() {
        return template;
    }

    public void setTemplate(Integer template) {
        this.template = template;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
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
        return "Email{" +
                "subject='" + subject + '\'' +
                ", template=" + template +
                ", to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}

