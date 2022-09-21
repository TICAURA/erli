package com.aura.qamm.model;

public class Language {
    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "Language{" +
                "lang='" + lang + '\'' +
                '}';
    }
}
