package com.aura.qamm.dto;

public class ValidaToken {

    private String email;
    private String celular;
    //private String


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @Override
    public String toString() {
        return "ValidaToken{" +
                "email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                '}';
    }
}

