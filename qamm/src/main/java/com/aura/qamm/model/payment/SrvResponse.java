package com.aura.qamm.model.payment;

public class SrvResponse {
    private String resCode;
    private String usrMsg;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getUsrMsg() {
        return usrMsg;
    }

    public void setUsrMsg(String usrMsg) {
        this.usrMsg = usrMsg;
    }

    @Override
    public String toString() {
        return "SrvResponse{" +
                "resCode='" + resCode + '\'' +
                ", usrMsg='" + usrMsg + '\'' +
                '}';
    }

    public SrvResponse(String resCode, String usrMsg) {
        this.resCode = resCode;
        this.usrMsg = usrMsg;
    }

    public SrvResponse() {
    }
}
