package com.application.model.entities;

import com.application.model.enumerations.ConsultationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Consultation {
	private UUID consultationId;
    private UUID patientId;
    private LocalDateTime consultationStartDateTime;
    private LocalDateTime consultationEndDateTime;
    private ConsultationStatus consultationStatus;
    private String ConsultationNotePath;
    private BigDecimal consultationAmount;
	private Boolean consultationAmountPaid;

	public Consultation(
			UUID consultationId,
			UUID patientId,
			LocalDateTime consultationStartDateTime,
			LocalDateTime consultationEndDateTime,
			ConsultationStatus consultationStatus,
			String consultationNotePath,
			BigDecimal consultationAmount,
			Boolean consultationAmountPaid) {
		this.consultationId = consultationId;
		this.patientId = patientId;
		this.consultationStartDateTime = consultationStartDateTime;
		this.consultationEndDateTime = consultationEndDateTime;
		this.consultationStatus = consultationStatus;
		this.ConsultationNotePath = consultationNotePath;
		this.consultationAmount = consultationAmount;
		this.consultationAmountPaid = consultationAmountPaid;
	}

	public UUID getConsultationId() {
		return consultationId;
	}

	public void setConsultationId(UUID consultationId) {
		this.consultationId = consultationId;
	}

	public UUID getPatientId() {
		return patientId;
	}

	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
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

	public String getConsultationNotePath() {
		return ConsultationNotePath;
	}

	public void setConsultationNotePath(String consultationNotePath) {
		ConsultationNotePath = consultationNotePath;
	}

	public BigDecimal getConsultationAmount() {
		return consultationAmount;
	}

	public void setConsultationAmount(BigDecimal consultationAmount) {
		this.consultationAmount = consultationAmount;
	}

	public Boolean getConsultationAmountPaid() {
		return consultationAmountPaid;
	}

	public void setConsultationAmountPaid(Boolean consultationAmountPaid) {
		this.consultationAmountPaid = consultationAmountPaid;
	}
}
