package com.aura.qamm.model;

public class AuthUser {

    private String user;
    private String password;
    private String personId;
    private String onboardDate;
    private String clientId;
    private String collaboratorId;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getOnboardDate() {
        return onboardDate;
    }

    public void setOnboardDate(String onboardDate) {
        this.onboardDate = onboardDate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(String collaboratorId) {
        this.collaboratorId = collaboratorId;
    }
}
