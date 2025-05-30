package com.application.model.dto;

public class ConsultationPatientDTO {
    private String consultationId;
    private String patientId;
    private String consultationAmount;
    private String isPaid;
    private String patientNotePath;

    public ConsultationPatientDTO() {
    }

    public ConsultationPatientDTO(
            String consultationId, 
            String patientId, 
            String consultationAmount, 
            String isPaid, 
            String patientNotePath) {
        this.consultationId = consultationId;
        this.patientId = patientId;
        this.consultationAmount = consultationAmount;
        this.isPaid = isPaid;
        this.patientNotePath = patientNotePath;
    }
    
    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getConsultationAmount() {
        return consultationAmount;
    }

    public void setConsultationAmount(String consultationAmount) {
        this.consultationAmount = consultationAmount;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getPatientNotePath() {
        return patientNotePath;
    }

    public void setPatientNotePath(String patientNotePath) {
        this.patientNotePath = patientNotePath;
    }   
}
