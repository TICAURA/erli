package com.aura.qamm.model.payroll;

import java.time.LocalDate;

public class UserPR {
    private String user;
    private String collaborator;
    private String account;
    private String linkItem;

    private Integer persId;
    private Integer clientId;
    private LocalDate onBoardingDate;

    private String email;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(String collaborator) {
        this.collaborator = collaborator;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLinkItem() {
        return linkItem;
    }

    public void setLinkItem(String linkItem) {
        this.linkItem = linkItem;
    }

    public Integer getPersId() {
        return persId;
    }

    public void setPersId(Integer persId) {
        this.persId = persId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public LocalDate getOnBoardingDate() {
        return onBoardingDate;
    }

    public void setOnBoardingDate(LocalDate onBoardingDate) {
        this.onBoardingDate = onBoardingDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserPR{" +
                "user='" + user + '\'' +
                ", collaborator='" + collaborator + '\'' +
                ", account='" + account + '\'' +
                ", linkItem='" + linkItem + '\'' +
                ", persId=" + persId +
                ", clientId=" + clientId +
                ", onBoardingDate=" + onBoardingDate +
                ", email='" + email + '\'' +
                '}';
    }
}
