package com.aura.qamm.dto;

public class CuentaBancaria {
    private Integer idColaborador;

    private Integer idBanco;
    private String nombreBanco;
    private String cuentaBanco;

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public CuentaBancaria() {
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getCuentaBanco() {
        return cuentaBanco;
    }

    public void setCuentaBanco(String cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    public CuentaBancaria(Integer idColaborador, Integer idBanco, String nombreBanco, String cuentaBanco) {
        this.idColaborador = idColaborador;
        this.idBanco = idBanco;
        this.nombreBanco = nombreBanco;
        this.cuentaBanco = cuentaBanco;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "idColaborador=" + idColaborador +
                ", idBanco=" + idBanco +
                ", nombreBanco='" + nombreBanco + '\'' +
                ", cuentaBanco='" + cuentaBanco + '\'' +
                '}';
    }
}
