package com.application.therapistapp.model.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Patient {
	private UUID patientId;
    private String patientDNI;
    private String patientName;
    private String patientLastName;
    private LocalDate patientBirthDate;
	private String patientPhone;
    private String patientEmail;
	private UUID cityId;
    private String patientAddress;
    private int patientAddressNumber;
    private int patientAddressFloor;
    private String patientAddressApartment;
    
	public Patient(
			UUID patientId, 
			String patientDNI, 
			String patientName, 
			String patientLastName,
			LocalDate patientBirthDate,
			String patientPhone,
			String patientEmail,
			UUID cityId,
			String patientAddress, 
			int patientAddressNumber,
			int patientAddressFloor, 
			String patientAddressApartment) {
		this.patientId = patientId;
		this.patientDNI = patientDNI;
		this.patientName = patientName;
		this.patientLastName = patientLastName;
		this.patientBirthDate = patientBirthDate;
		this.patientPhone = patientPhone;
		this.patientEmail = patientEmail;
		this.cityId = cityId;
		this.patientAddress = patientAddress;
		this.patientAddressNumber = patientAddressNumber;
		this.patientAddressFloor = patientAddressFloor;
		this.patientAddressApartment = patientAddressApartment;
	}
	
	public UUID getPatientId() {
		return patientId;
	}
	
	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}
	
	public String getPatientDNI() {
		return patientDNI;
	}
	
	public void setPatientDNI(String patientDNI) {
		this.patientDNI = patientDNI;
	}
	
	public String getPatientName() {
		return patientName;
	}
	
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	public String getPatientLastName() {
		return patientLastName;
	}
	
	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}
	
	public LocalDate getPatientBirthDate() {
		return patientBirthDate;
	}
	
	public void setPatientBirthDate(LocalDate patientBirthDate) {
		this.patientBirthDate = patientBirthDate;
	}

	public String getPatientPhone() {
		return patientPhone;
	}

	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}

	public String getPatientEmail() {
		return patientEmail;
	}
	
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public UUID getCityId() {
		return cityId;
	}

	public void setCityId(UUID cityId) {
		this.cityId = cityId;
	}
	
	public String getPatientAddress() {
		return patientAddress;
	}
	
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	
	public int getPatientAddressNumber() {
		return patientAddressNumber;
	}
	
	public void setPatientAddressNumber(int patientAddressNumber) {
		this.patientAddressNumber = patientAddressNumber;
	}
	
	public int getPatientAddressFloor() {
		return patientAddressFloor;
	}
	
	public void setPatientAddressFloor(int patientAddressFloor) {
		this.patientAddressFloor = patientAddressFloor;
	}
	
	public String getPatientAddressApartment() {
		return patientAddressApartment;
	}
	
	public void setPatientAddressApartment(String patientAddressApartment) {
		this.patientAddressApartment = patientAddressApartment;
	}

}
