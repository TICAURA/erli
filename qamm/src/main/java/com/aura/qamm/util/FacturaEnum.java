package com.aura.qamm.util;

public enum FacturaEnum {
    FACTURANDO(1,	"enviado a facturar"),
    FACTURADO(2,"facturado"),
    GUARDADO(3,"guardado"),
    ENVIADO_A_USUARIO(4,"enviado a usuario");

    private int id;
    private String status;


    private FacturaEnum(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
