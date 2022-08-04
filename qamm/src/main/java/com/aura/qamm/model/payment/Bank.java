package com.aura.qamm.model.payment;

public class Bank {
    private String routingNumber;

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "routingNumber='" + routingNumber + '\'' +
                '}';
    }
}
