package com.aura.qamm.util;

public enum DispersorENUM {
    STP(0),
    BANPAY(1),
    PRUEBA(2);


    private int id;


    private DispersorENUM(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
