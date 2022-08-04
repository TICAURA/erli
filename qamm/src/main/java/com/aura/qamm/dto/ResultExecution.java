package com.aura.qamm.dto;

public class ResultExecution {
    private String mensajeApp;
    private Integer idColaborador;

    public String getMensajeApp() {
        return mensajeApp;
    }

    public void setMensajeApp(String mensajeApp) {
        this.mensajeApp = mensajeApp;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    @Override
    public String toString() {
        return "ResultExecution{" +
                "mensajeApp='" + mensajeApp + '\'' +
                ", idColaborador=" + idColaborador +
                '}';
    }
}
