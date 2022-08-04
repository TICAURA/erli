package com.aura.qamm.dto;

public class Password {
    private String newPW;
    private String oldPW;


    public String getNewPW() {
        return newPW;
    }

    public void setNewPW(String newPW) {
        this.newPW = newPW;
    }

    public String getOldPW() {
        return oldPW;
    }

    public void setOldPW(String oldPW) {
        this.oldPW = oldPW;
    }


    @Override
    public String toString() {
        return "Password{" +
                "newPW='" + newPW + '\'' +
                ", oldPW='" + oldPW + '\'' +
                '}';
    }
}
