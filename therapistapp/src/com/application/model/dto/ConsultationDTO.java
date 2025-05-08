package com.application.model.dto;

public class ConsultationDTO {
	private String consultationDTOId;
	private String patientId;
	private String consultationDTOStartDateTime;
	private String consultationDTOEndDateTime;
	private String consultationDTOStatus;
	private String ConsultationDTONotePath;
	private String consultationDTOAmount;
	private String consultationDTOAmountPaid;

	public ConsultationDTO(
			String patientId,
			String consultationDTOStartDateTime,
			String consultationDTOEndDateTime,
			String consultationDTOStatus,
			String consultationDTONotePath,
			String consultationDTOAmount,
			String consultationDTOAmountPaid) {
		this.patientId = patientId;
		this.consultationDTOStartDateTime = consultationDTOStartDateTime;
		this.consultationDTOEndDateTime = consultationDTOEndDateTime;
		this.consultationDTOStatus = consultationDTOStatus;
		this.ConsultationDTONotePath = consultationDTONotePath;
		this.consultationDTOAmount = consultationDTOAmount;
		this.consultationDTOAmountPaid = consultationDTOAmountPaid;
	}

	public ConsultationDTO(
			String consultationDTOId,
			String patientId,
			String consultationDTOStartDateTime,
			String consultationDTOEndDateTime,
			String consultationDTOStatus,
			String consultationDTONotePath,
			String consultationDTOAmount,
			String consultationDTOAmountPaid) {
		this.consultationDTOId = consultationDTOId;
		this.patientId = patientId;
		this.consultationDTOStartDateTime = consultationDTOStartDateTime;
		this.consultationDTOEndDateTime = consultationDTOEndDateTime;
		this.consultationDTOStatus = consultationDTOStatus;
		this.ConsultationDTONotePath = consultationDTONotePath;
		this.consultationDTOAmount = consultationDTOAmount;
		this.consultationDTOAmountPaid = consultationDTOAmountPaid;
	}

	public String getConsultationDTOId() {
		return consultationDTOId;
	}

	public void setConsultationDTOId(String consultationDTOId) {
		this.consultationDTOId = consultationDTOId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
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

	public String getConsultationDTONotePath() {
		return ConsultationDTONotePath;
	}

	public void setConsultationDTONotePath(String consultationDTONotePath) {
		ConsultationDTONotePath = consultationDTONotePath;
	}

	public String getConsultationDTOAmount() {
		return consultationDTOAmount;
	}

	public void setConsultationDTOAmount(String consultationDTOAmount) {
		this.consultationDTOAmount = consultationDTOAmount;
	}

	public String getConsultationDTOAmountPaid() {
		return consultationDTOAmountPaid;
	}

	public void setConsultationDTOAmountPaid(String consultationDTOAmountPaid) {
		this.consultationDTOAmountPaid = consultationDTOAmountPaid;
	}

}
