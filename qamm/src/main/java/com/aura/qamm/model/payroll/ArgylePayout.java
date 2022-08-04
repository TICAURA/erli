package com.aura.qamm.model.payroll;

import java.time.LocalDate;

public class ArgylePayout {

    private String pyId;
    private String pyDocumentId;
    private String pyEmployer;
    private String pyStatus;
    private String pyType;
    private LocalDate pyPayoutDate;
    private LocalDate pyPayoutPeriodStart;
    private LocalDate pyPayoutPeriodEnd;
    private String pyCurrency;
    private String pyGrossPay;
    private String pyReimbursements;
    private String pyDeductions;
    private String pyTaxes;
    private String pyFees;
    private String pyNetPay;
    private String pyBonuses;
    private String pyCommission;
    private String pyOvertime;
    private String pyHours;
    private String pyEmpAddLine1;
    private String pyEmpAddLine2;
    private String pyEmpAddCity;
    private String pyEmpAddState;
    private String pyEmpAddPostalCode;
    private String pyCountry;
    private String pyMetaData;
    private String pyAccount;

    public String getPyId() {
        return pyId;
    }

    public void setPyId(String pyId) {
        this.pyId = pyId;
    }

    public String getPyDocumentId() {
        return pyDocumentId;
    }

    public void setPyDocumentId(String pyDocumentId) {
        this.pyDocumentId = pyDocumentId;
    }

    public String getPyEmployer() {
        return pyEmployer;
    }

    public void setPyEmployer(String pyEmployer) {
        this.pyEmployer = pyEmployer;
    }

    public String getPyStatus() {
        return pyStatus;
    }

    public void setPyStatus(String pyStatus) {
        this.pyStatus = pyStatus;
    }

    public String getPyType() {
        return pyType;
    }

    public void setPyType(String pyType) {
        this.pyType = pyType;
    }

    public LocalDate getPyPayoutDate() {
        return pyPayoutDate;
    }

    public void setPyPayoutDate(LocalDate pyPayoutDate) {
        this.pyPayoutDate = pyPayoutDate;
    }

    public LocalDate getPyPayoutPeriodStart() {
        return pyPayoutPeriodStart;
    }

    public void setPyPayoutPeriodStart(LocalDate pyPayoutPeriodStart) {
        this.pyPayoutPeriodStart = pyPayoutPeriodStart;
    }

    public LocalDate getPyPayoutPeriodEnd() {
        return pyPayoutPeriodEnd;
    }

    public void setPyPayoutPeriodEnd(LocalDate pyPayoutPeriodEnd) {
        this.pyPayoutPeriodEnd = pyPayoutPeriodEnd;
    }

    public String getPyCurrency() {
        return pyCurrency;
    }

    public void setPyCurrency(String pyCurrency) {
        this.pyCurrency = pyCurrency;
    }

    public String getPyGrossPay() {
        return pyGrossPay;
    }

    public void setPyGrossPay(String pyGrossPay) {
        this.pyGrossPay = pyGrossPay;
    }

    public String getPyReimbursements() {
        return pyReimbursements;
    }

    public void setPyReimbursements(String pyReimbursements) {
        this.pyReimbursements = pyReimbursements;
    }

    public String getPyDeductions() {
        return pyDeductions;
    }

    public void setPyDeductions(String pyDeductions) {
        this.pyDeductions = pyDeductions;
    }

    public String getPyTaxes() {
        return pyTaxes;
    }

    public void setPyTaxes(String pyTaxes) {
        this.pyTaxes = pyTaxes;
    }

    public String getPyFees() {
        return pyFees;
    }

    public void setPyFees(String pyFees) {
        this.pyFees = pyFees;
    }

    public String getPyNetPay() {
        return pyNetPay;
    }

    public void setPyNetPay(String pyNetPay) {
        this.pyNetPay = pyNetPay;
    }

    public String getPyBonuses() {
        return pyBonuses;
    }

    public void setPyBonuses(String pyBonuses) {
        this.pyBonuses = pyBonuses;
    }

    public String getPyCommission() {
        return pyCommission;
    }

    public void setPyCommission(String pyCommission) {
        this.pyCommission = pyCommission;
    }

    public String getPyOvertime() {
        return pyOvertime;
    }

    public void setPyOvertime(String pyOvertime) {
        this.pyOvertime = pyOvertime;
    }

    public String getPyHours() {
        return pyHours;
    }

    public void setPyHours(String pyHours) {
        this.pyHours = pyHours;
    }

    public String getPyEmpAddLine1() {
        return pyEmpAddLine1;
    }

    public void setPyEmpAddLine1(String pyEmpAddLine1) {
        this.pyEmpAddLine1 = pyEmpAddLine1;
    }

    public String getPyEmpAddLine2() {
        return pyEmpAddLine2;
    }

    public void setPyEmpAddLine2(String pyEmpAddLine2) {
        this.pyEmpAddLine2 = pyEmpAddLine2;
    }

    public String getPyEmpAddCity() {
        return pyEmpAddCity;
    }

    public void setPyEmpAddCity(String pyEmpAddCity) {
        this.pyEmpAddCity = pyEmpAddCity;
    }

    public String getPyEmpAddState() {
        return pyEmpAddState;
    }

    public void setPyEmpAddState(String pyEmpAddState) {
        this.pyEmpAddState = pyEmpAddState;
    }

    public String getPyEmpAddPostalCode() {
        return pyEmpAddPostalCode;
    }

    public void setPyEmpAddPostalCode(String pyEmpAddPostalCode) {
        this.pyEmpAddPostalCode = pyEmpAddPostalCode;
    }

    public String getPyCountry() {
        return pyCountry;
    }

    public void setPyCountry(String pyCountry) {
        this.pyCountry = pyCountry;
    }

    public String getPyMetaData() {
        return pyMetaData;
    }

    public void setPyMetaData(String pyMetaData) {
        this.pyMetaData = pyMetaData;
    }

    public String getPyAccount() {
        return pyAccount;
    }

    public void setPyAccount(String pyAccount) {
        this.pyAccount = pyAccount;
    }

    @Override
    public String toString() {
        return "ArgylePayout{" +
                "pyId='" + pyId + '\'' +
                ", pyDocumentId='" + pyDocumentId + '\'' +
                ", pyEmployer='" + pyEmployer + '\'' +
                ", pyStatus='" + pyStatus + '\'' +
                ", pyType='" + pyType + '\'' +
                ", pyPayoutDate=" + pyPayoutDate +
                ", pyPayoutPeriodStart=" + pyPayoutPeriodStart +
                ", pyPayoutPeriodEnd=" + pyPayoutPeriodEnd +
                ", pyCurrency='" + pyCurrency + '\'' +
                ", pyGrossPay='" + pyGrossPay + '\'' +
                ", pyReimbursements='" + pyReimbursements + '\'' +
                ", pyDeductions='" + pyDeductions + '\'' +
                ", pyTaxes='" + pyTaxes + '\'' +
                ", pyFees='" + pyFees + '\'' +
                ", pyNetPay='" + pyNetPay + '\'' +
                ", pyBonuses='" + pyBonuses + '\'' +
                ", pyCommission='" + pyCommission + '\'' +
                ", pyOvertime='" + pyOvertime + '\'' +
                ", pyHours='" + pyHours + '\'' +
                ", pyEmpAddLine1='" + pyEmpAddLine1 + '\'' +
                ", pyEmpAddLine2='" + pyEmpAddLine2 + '\'' +
                ", pyEmpAddCity='" + pyEmpAddCity + '\'' +
                ", pyEmpAddState='" + pyEmpAddState + '\'' +
                ", pyEmpAddPostalCode='" + pyEmpAddPostalCode + '\'' +
                ", pyCountry='" + pyCountry + '\'' +
                ", pyMetaData='" + pyMetaData + '\'' +
                ", pyAccount='" + pyAccount + '\'' +
                '}';
    }
}
