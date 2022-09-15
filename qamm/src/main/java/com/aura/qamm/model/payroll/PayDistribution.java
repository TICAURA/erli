package com.aura.qamm.model.payroll;

public class PayDistribution {
    private String user;
    private String amount;
    private String montoAculumadoDistribucionArgyle;
    private String fee;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMontoAculumadoDistribucionArgyle() {
        return montoAculumadoDistribucionArgyle;
    }

    public void setMontoAculumadoDistribucionArgyle(String montoAculumadoDistribucionArgyle) {
        this.montoAculumadoDistribucionArgyle = montoAculumadoDistribucionArgyle;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "PayDistribution{" +
                "user='" + user + '\'' +
                ", amount='" + amount + '\'' +
                ", montoAculumadoDistribucionArgyle='" + montoAculumadoDistribucionArgyle + '\'' +
                ", fee='" + fee + '\'' +
                '}';
    }
}
