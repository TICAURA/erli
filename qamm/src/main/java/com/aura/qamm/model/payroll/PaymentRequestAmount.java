package com.aura.qamm.model.payroll;

public class PaymentRequestAmount {
    private Integer collaboratorId;
    private String requestedAmount;
    private String commisionAmount;
    private String totalAmount;
    private Double promotionAmount;
    private Integer promotionId;

    public Integer getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(Integer collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    public String getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(String requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getCommisionAmount() {
        return commisionAmount;
    }

    public void setCommisionAmount(String commisionAmount) {
        this.commisionAmount = commisionAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(Double promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    @Override
    public String toString() {
        return "PaymentRequestCF{" +
                "idCollaborator=" + collaboratorId +
                ", requestedAmount='" + requestedAmount + '\'' +
                ", commisionAmount='" + commisionAmount + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", promotionAmount=" + promotionAmount +
                ", promotionId=" + promotionId +
                '}';
    }

}
