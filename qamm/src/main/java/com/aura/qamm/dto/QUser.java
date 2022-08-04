package com.aura.qamm.dto;

public class QUser {
    private String idColaborador;
    private String password;
    private String nombre;
    private String apaterno;
    private String amaterno;

    private String email;
    private String rfc;
    private String celular;

    private String mensajeApp;

    private String errorCode;
    private String mensajeApp_es;
    private String mensajeApp_en;

    private String argyle_user_id;

    public String getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(String idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMensajeApp() {
        return mensajeApp;
    }

    public void setMensajeApp(String mensajeApp) {
        this.mensajeApp = mensajeApp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMensajeApp_es() {
        return mensajeApp_es;
    }

    public void setMensajeApp_es(String mensajeApp_es) {
        this.mensajeApp_es = mensajeApp_es;
    }

    public String getMensajeApp_en() {
        return mensajeApp_en;
    }

    public void setMensajeApp_en(String mensajeApp_en) {
        this.mensajeApp_en = mensajeApp_en;
    }

    public String getArgyle_user_id() {
        return argyle_user_id;
    }

    public void setArgyle_user_id(String argyle_user_id) {
        this.argyle_user_id = argyle_user_id;
    }

    @Override
    public String toString() {
        return "QUser{" +
                "idColaborador='" + idColaborador + '\'' +
                ", password='" + password + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apaterno='" + apaterno + '\'' +
                ", amaterno='" + amaterno + '\'' +
                ", email='" + email + '\'' +
                ", rfc='" + rfc + '\'' +
                ", celular='" + celular + '\'' +
                ", mensajeApp='" + mensajeApp + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", mensajeApp_es='" + mensajeApp_es + '\'' +
                ", mensajeApp_en='" + mensajeApp_en + '\'' +
                ", argyle_user_id='" + argyle_user_id + '\'' +
                '}';
    }
}
