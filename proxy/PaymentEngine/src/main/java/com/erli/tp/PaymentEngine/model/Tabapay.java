package com.erli.tp.PaymentEngine.model;

public class Tabapay {
    private String transactionID; //TP
    private String requestBody;
    private String responseBody;
    private String erliTransaction;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getErliTransaction() {
        return erliTransaction;
    }

    public void setErliTransaction(String erliTransaction) {
        this.erliTransaction = erliTransaction;
    }

    @Override
    public String toString() {
        return "Tabapay{" +
                "transactionID='" + transactionID + '\'' +
                ", requestBody='" + requestBody + '\'' +
                ", responseBody='" + responseBody + '\'' +
                ", erliTransaction='" + erliTransaction + '\'' +
                '}';
    }
}
