package com.aura.qamm.model.payroll;

public class PayDistribution {
    private String user;
    private String amount;
    private String commision;

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

    public String getCommision() {
        return commision;
    }

    public void setCommision(String commision) {
        this.commision = commision;
    }

    @Override
    public String toString() {
        return "PayDistribution{" +
                "user='" + user + '\'' +
                ", amount='" + amount + '\'' +
                ", commision='" + commision + '\'' +
                '}';
    }
}
