package com.aura.qamm.model.payroll;

import java.time.LocalDate;

public class ArgyleEmployment {
    private String eId;
    private String eEmployer;
    private String eStatus;
    private String eType;
    private String eJobTitle;
    //private String eHireDatetime;
    private LocalDate eHireDatetime;
    private String eTerminationDatetime;
    private String eTerminationReason;
    private String eBasePayAmount;
    private String eBasePayPeriod;
    private String eBasePayCurrency;
    private String ePayCycle;
    private String ePlatformIdsEmployeeId;
    private String ePlatformIdsPositionId;
    private String ePlatformIdsUserId;
    private String eMetaData;
    private String eAccount;
    private String eJson;

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public String geteEmployer() {
        return eEmployer;
    }

    public void seteEmployer(String eEmployer) {
        this.eEmployer = eEmployer;
    }

    public String geteStatus() {
        return eStatus;
    }

    public void seteStatus(String eStatus) {
        this.eStatus = eStatus;
    }

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

    public String geteJobTitle() {
        return eJobTitle;
    }

    public void seteJobTitle(String eJobTitle) {
        this.eJobTitle = eJobTitle;
    }

    /*public String geteHireDatetime() {
        return eHireDatetime;
    }

    public void seteHireDatetime(String eHireDatetime) {
        this.eHireDatetime = eHireDatetime;
    }*/

    public LocalDate geteHireDatetime() {
        return eHireDatetime;
    }

    public void seteHireDatetime(LocalDate eHireDatetime) {
        this.eHireDatetime = eHireDatetime;
    }

    public String geteTerminationDatetime() {
        return eTerminationDatetime;
    }

    public void seteTerminationDatetime(String eTerminationDatetime) {
        this.eTerminationDatetime = eTerminationDatetime;
    }

    public String geteTerminationReason() {
        return eTerminationReason;
    }

    public void seteTerminationReason(String eTerminationReason) {
        this.eTerminationReason = eTerminationReason;
    }

    public String geteBasePayAmount() {
        return eBasePayAmount;
    }

    public void seteBasePayAmount(String eBasePayAmount) {
        this.eBasePayAmount = eBasePayAmount;
    }

    public String geteBasePayPeriod() {
        return eBasePayPeriod;
    }

    public void seteBasePayPeriod(String eBasePayPeriod) {
        this.eBasePayPeriod = eBasePayPeriod;
    }

    public String geteBasePayCurrency() {
        return eBasePayCurrency;
    }

    public void seteBasePayCurrency(String eBasePayCurrency) {
        this.eBasePayCurrency = eBasePayCurrency;
    }

    public String getePayCycle() {
        return ePayCycle;
    }

    public void setePayCycle(String ePayCycle) {
        this.ePayCycle = ePayCycle;
    }

    public String getePlatformIdsEmployeeId() {
        return ePlatformIdsEmployeeId;
    }

    public void setePlatformIdsEmployeeId(String ePlatformIdsEmployeeId) {
        this.ePlatformIdsEmployeeId = ePlatformIdsEmployeeId;
    }

    public String getePlatformIdsPositionId() {
        return ePlatformIdsPositionId;
    }

    public void setePlatformIdsPositionId(String ePlatformIdsPositionId) {
        this.ePlatformIdsPositionId = ePlatformIdsPositionId;
    }

    public String getePlatformIdsUserId() {
        return ePlatformIdsUserId;
    }

    public void setePlatformIdsUserId(String ePlatformIdsUserId) {
        this.ePlatformIdsUserId = ePlatformIdsUserId;
    }

    public String geteMetaData() {
        return eMetaData;
    }

    public void seteMetaData(String eMetaData) {
        this.eMetaData = eMetaData;
    }

    public String geteAccount() {
        return eAccount;
    }

    public void seteAccount(String eAccount) {
        this.eAccount = eAccount;
    }

    public String geteJson() {
        return eJson;
    }

    public void seteJson(String eJson) {
        this.eJson = eJson;
    }

    @Override
    public String toString() {
        return "ArgyleEmployment{" +
                "eId='" + eId + '\'' +
                ", eEmployer='" + eEmployer + '\'' +
                ", eStatus='" + eStatus + '\'' +
                ", eType='" + eType + '\'' +
                ", eJobTitle='" + eJobTitle + '\'' +
                ", eHireDatetime=" + eHireDatetime +
                ", eTerminationDatetime='" + eTerminationDatetime + '\'' +
                ", eTerminationReason='" + eTerminationReason + '\'' +
                ", eBasePayAmount='" + eBasePayAmount + '\'' +
                ", eBasePayPeriod='" + eBasePayPeriod + '\'' +
                ", eBasePayCurrency='" + eBasePayCurrency + '\'' +
                ", ePayCycle='" + ePayCycle + '\'' +
                ", ePlatformIdsEmployeeId='" + ePlatformIdsEmployeeId + '\'' +
                ", ePlatformIdsPositionId='" + ePlatformIdsPositionId + '\'' +
                ", ePlatformIdsUserId='" + ePlatformIdsUserId + '\'' +
                ", eMetaData='" + eMetaData + '\'' +
                ", eAccount='" + eAccount + '\'' +
                ", eJson='" + eJson + '\'' +
                '}';
    }
}
