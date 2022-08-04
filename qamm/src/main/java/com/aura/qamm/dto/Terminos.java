package com.aura.qamm.dto;

public class Terminos {
    private String mensajeApp;
    private String valor;
    private String etiqueta;

    private String paramTerminos;

    public String getMensajeApp() {
        return mensajeApp;
    }

    public void setMensajeApp(String mensajeApp) {
        this.mensajeApp = mensajeApp;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getParamTerminos() {
        return paramTerminos;
    }

    public void setParamTerminos(String paramTerminos) {
        this.paramTerminos = paramTerminos;
    }

    @Override
    public String toString() {
        return "Terminos{" +
                "mensajeApp='" + mensajeApp + '\'' +
                ", valor='" + valor + '\'' +
                ", etiqueta='" + etiqueta + '\'' +
                ", paramTerminos='" + paramTerminos + '\'' +
                '}';
    }
}
