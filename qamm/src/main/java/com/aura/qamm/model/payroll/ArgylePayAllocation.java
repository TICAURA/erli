package com.aura.qamm.model.payroll;

public class ArgylePayAllocation {

    private String idPAyAlloc;
    private Integer routingNumber;
    private Long accountNumber;
    private String accountType;
    private Integer persId;
    private String json;

    private String allocationValue;
    private String allocationId;
    private String allocationAcount;

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

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
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

    public String getAllocationValue() {
        return allocationValue;
    }

    public void setAllocationValue(String allocationValue) {
        this.allocationValue = allocationValue;
    }

    public String getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(String allocationId) {
        this.allocationId = allocationId;
    }

    public String getAllocationAcount() {
        return allocationAcount;
    }

    public void setAllocationAcount(String allocationAcount) {
        this.allocationAcount = allocationAcount;
    }
}
