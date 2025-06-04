package com.application.model.dto;

public class ConsultationDTO {
    private String consultationDTOId;
    private String consultationDTOStartDateTime;
    private String consultationDTOAmount;
    private String consultationDTOEndDateTime;
    private String consultationDTOStatus;

    public ConsultationDTO() {
    }

    public ConsultationDTO(
            String consultationDTOId, 
            String consultationDTOStartDateTime, 
            String consultationDTOAmount, 
            String consultationDTOEndDateTime, 
            String consultationDTOStatus) {
        this.consultationDTOId = consultationDTOId;
        this.consultationDTOStartDateTime = consultationDTOStartDateTime;
        this.consultationDTOAmount = consultationDTOAmount;
        this.consultationDTOEndDateTime = consultationDTOEndDateTime;
        this.consultationDTOStatus = consultationDTOStatus;
    }

    public String getConsultationDTOId() {
        return consultationDTOId;
    }

    public void setConsultationDTOId(String consultationDTOId) {
        this.consultationDTOId = consultationDTOId;
    }

    public String getConsultationDTOStartDateTime() {
        return consultationDTOStartDateTime;
    }

    public void setConsultationDTOStartDateTime(String consultationDTOStartDateTime) {
        this.consultationDTOStartDateTime = consultationDTOStartDateTime;
    }

    public String getConsultationDTOAmount() {
        return consultationDTOAmount;
    }

    public void setConsultationDTOAmount(String consultationDTOAmount) {
        this.consultationDTOAmount = consultationDTOAmount;
    }

    public String getConsultationDTOEndDateTime() {
        return consultationDTOEndDateTime;
    }

    public void setConsultationDTOEndDateTime(String consultationDTOEndDateTime) {
        this.consultationDTOEndDateTime = consultationDTOEndDateTime;
    }

    public String getConsultationDTOStatus() {
        return consultationDTOStatus;
    }

    public void setConsultationDTOStatus(String consultationDTOStatus) {
        this.consultationDTOStatus = consultationDTOStatus;
    }

    public String getConsultationDTODate() {
        return consultationDTOStartDateTime.substring(0,10);      
    }
    
    public String getConsultationDTOStartTime() {
        return consultationDTOStartDateTime.substring(11,16);      
    }
    
    public String getConsultationDTOEndTime() {
        return consultationDTOEndDateTime.substring(11,16);
    }
}
