package com.aura.qamm.model;

public class Acumulator {
    private Long cveColaborador;

    public Long getCveColaborador() {
        return cveColaborador;
    }

    public void setCveColaborador(Long cveColaborador) {
        this.cveColaborador = cveColaborador;
    }

    @Override
    public String toString() {
        return "Acumulator{" +
                "cveColaborador=" + cveColaborador +
                '}';
    }
}
