package com.aura.qamm.model.payroll;

public class ArgylePayAllocation {

    private String idPAyAlloc;
    private Integer routingNumber;
    private Integer accountNumber;
    private String accountType;
    private Integer persId;
    private String json;

    public String getIdPAyAlloc() {
        return idPAyAlloc;
    }

    public void setIdPAyAlloc(String idPAyAlloc) {
        this.idPAyAlloc = idPAyAlloc;
    }

    public Integer getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(Integer routingNumber) {
        this.routingNumber = routingNumber;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Integer getPersId() {
        return persId;
    }

    public void setPersId(Integer persId) {
        this.persId = persId;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
