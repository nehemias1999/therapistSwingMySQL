package com.application.model.dto;

public class ConsultationDTO {
    private String consultationDTOId;
    private String consultationDTOStartDateTime;
    private String consultationDTOEndDateTime;
    private String consultationDTOStatus;

    public ConsultationDTO() {
    }

    public ConsultationDTO(
            String consultationDTOId, 
            String consultationDTOStartDateTime, 
            String consultationDTOEndDateTime, 
            String consultationDTOStatus) {
        this.consultationDTOId = consultationDTOId;
        this.consultationDTOStartDateTime = consultationDTOStartDateTime;
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
    
    public String getConsultationDTOStartTime() {
        return consultationDTOEndDateTime.substring(11,16);
        
    }
    
    public String getConsultationDTOEndTime() {
        return consultationDTOEndDateTime.substring(11,16);
    }
}
