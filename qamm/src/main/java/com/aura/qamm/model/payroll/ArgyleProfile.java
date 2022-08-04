package com.aura.qamm.model.payroll;

import java.time.LocalDate;

public class ArgyleProfile {
    private String pId;
    private String pAccount;
    private String pEmployer;
    private String pFirstName;
    private String pLastName;
    private String pFullName;
    private String pEmail;
    private String pPhoneNumber;
    private LocalDate pBirthDate;
    private String pPictureURL;
    private String pSsn;
    private String pMaritalStatus;
    private String pGender;
    private String pMetaData;
    private String pJson;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpAccount() {
        return pAccount;
    }

    public void setpAccount(String pAccount) {
        this.pAccount = pAccount;
    }

    public String getpEmployer() {
        return pEmployer;
    }

    public void setpEmployer(String pEmployer) {
        this.pEmployer = pEmployer;
    }

    public String getpFirstName() {
        return pFirstName;
    }

    public void setpFirstName(String pFirstName) {
        this.pFirstName = pFirstName;
    }

    public String getpLastName() {
        return pLastName;
    }

    public void setpLastName(String pLastName) {
        this.pLastName = pLastName;
    }

    public String getpFullName() {
        return pFullName;
    }

    public void setpFullName(String pFullName) {
        this.pFullName = pFullName;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getpPhoneNumber() {
        return pPhoneNumber;
    }

    public void setpPhoneNumber(String pPhoneNumber) {
        this.pPhoneNumber = pPhoneNumber;
    }

    public LocalDate getpBirthDate() {
        return pBirthDate;
    }

    public void setpBirthDate(LocalDate pBirthDate) {
        this.pBirthDate = pBirthDate;
    }

    public String getpSsn() {
        return pSsn;
    }

    public String getpPictureURL() {
        return pPictureURL;
    }

    public void setpPictureURL(String pPictureURL) {
        this.pPictureURL = pPictureURL;
    }

    public void setpSsn(String pSsn) {
        this.pSsn = pSsn;
    }

    public String getpMaritalStatus() {
        return pMaritalStatus;
    }

    public void setpMaritalStatus(String pMaritalStatus) {
        this.pMaritalStatus = pMaritalStatus;
    }

    public String getpGender() {
        return pGender;
    }

    public void setpGender(String pGender) {
        this.pGender = pGender;
    }

    public String getpMetaData() {
        return pMetaData;
    }

    public void setpMetaData(String pMetaData) {
        this.pMetaData = pMetaData;
    }

    public String getpJson() {
        return pJson;
    }

    public void setpJson(String pJson) {
        this.pJson = pJson;
    }

    @Override
    public String toString() {
        return "ArgyleProfile{" +
                "pId='" + pId + '\'' +
                ", pAccount='" + pAccount + '\'' +
                ", pEmployer='" + pEmployer + '\'' +
                ", pFirstName='" + pFirstName + '\'' +
                ", pLastName='" + pLastName + '\'' +
                ", pFullName='" + pFullName + '\'' +
                ", pEmail='" + pEmail + '\'' +
                ", pPhoneNumber='" + pPhoneNumber + '\'' +
                ", pBirthDate=" + pBirthDate +
                ", pPictureURL='" + pPictureURL + '\'' +
                ", pSsn='" + pSsn + '\'' +
                ", pMaritalStatus='" + pMaritalStatus + '\'' +
                ", pGender='" + pGender + '\'' +
                ", pMetaData='" + pMetaData + '\'' +
                ", pJson='" + pJson + '\'' +
                '}';
    }
}
