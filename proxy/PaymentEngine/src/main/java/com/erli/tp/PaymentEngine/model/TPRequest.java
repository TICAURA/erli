package com.erli.tp.PaymentEngine.model;

public class TPRequest {
    private String payload;
    private String idErliTransaction;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getIdErliTransaction() {
        return idErliTransaction;
    }

    public void setIdErliTransaction(String idErliTransaction) {
        this.idErliTransaction = idErliTransaction;
    }

    @Override
    public String toString() {
        return "TPRequest{" +
                "payload='" + payload + '\'' +
                ", idErliTransaction='" + idErliTransaction + '\'' +
                '}';
    }


}
