package com.aura.qamm.model.payroll;

public class EncryptedItem {
    private String encString;
    private String key;

    public String getEncString() {
        return encString;
    }

    public void setEncString(String encString) {
        this.encString = encString;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "EncryptedItem{" +
                "encString='" + encString + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public EncryptedItem(String encString, String key) {
        this.encString = encString;
        this.key = key;
    }

    public EncryptedItem() {
    }
}
