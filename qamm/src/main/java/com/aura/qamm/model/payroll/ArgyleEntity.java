package com.aura.qamm.model.payroll;

public class ArgyleEntity {
    private int index;
    private String type;
    private String value;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ArgyleEntity{" +
                "index=" + index +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public ArgyleEntity(int index, String type, String value) {
        this.index = index;
        this.type = type;
        this.value = value;
    }

    public ArgyleEntity() {
    }

    public ArgyleEntity(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
