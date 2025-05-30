package com.application.model.entities;

import com.application.model.enumerations.ConsultationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Consultation {
    private UUID consultationId;
    private LocalDateTime consultationStartDateTime;
    private LocalDateTime consultationEndDateTime;
    private ConsultationStatus consultationStatus;

    public Consultation(
            UUID consultationId, 
            LocalDateTime consultationStartDateTime, 
            LocalDateTime consultationEndDateTime, 
            ConsultationStatus consultationStatus) {
        this.consultationId = consultationId;
        this.consultationStartDateTime = consultationStartDateTime;
        this.consultationEndDateTime = consultationEndDateTime;
        this.consultationStatus = consultationStatus;
    }

    public UUID getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
    }

    public LocalDateTime getConsultationStartDateTime() {
        return consultationStartDateTime;
    }

    public void setConsultationStartDateTime(LocalDateTime consultationStartDateTime) {
        this.consultationStartDateTime = consultationStartDateTime;
    }

    public LocalDateTime getConsultationEndDateTime() {
        return consultationEndDateTime;
    }

    public void setConsultationEndDateTime(LocalDateTime consultationEndDateTime) {
        this.consultationEndDateTime = consultationEndDateTime;
    }

    public ConsultationStatus getConsultationStatus() {
        return consultationStatus;
    }

    public void setConsultationStatus(ConsultationStatus consultationStatus) {
        this.consultationStatus = consultationStatus;
    }
}
