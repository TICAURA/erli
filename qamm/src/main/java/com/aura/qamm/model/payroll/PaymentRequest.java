package com.aura.qamm.model.payroll;

public class PaymentRequest {
    private String paymetRequest;

    public String getPaymetRequest() {
        return paymetRequest;
    }

    public void setPaymetRequest(String paymetRequest) {
        this.paymetRequest = paymetRequest;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "paymetRequest='" + paymetRequest + '\'' +
                '}';
    }
}
