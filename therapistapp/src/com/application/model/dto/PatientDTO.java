package com.application.model.dto;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class PatientDTO {
	private String patientDTOId;
	private String patientDTODNI;
	private String patientDTOName;
	private String patientDTOLastName;
	private String patientDTOBirthDate;
	private String patientDTOPhone;
	private String patientDTOEmail;
	private String cityId;
	private String patientDTOAddress;
	private String patientDTOAddressNumber;
	private String patientDTOAddressFloor;
	private String patientDTOAddressApartment;

    public PatientDTO(
                String patientDTODNI,
                String patientDTOName,
                String patientDTOLastName,
                String patientDTOBirthDate,
                String patientDTOPhone,
                String patientDTOEmail,
                String cityId,
                String patientDTOAddress,
                String patientDTOAddressNumber,
                String patientDTOAddressFloor,
                String patientDTOAddressApartment) {
        this.patientDTODNI = patientDTODNI;
        this.patientDTOName = patientDTOName;
        this.patientDTOLastName = patientDTOLastName;
        this.patientDTOBirthDate = patientDTOBirthDate;
        this.patientDTOPhone = patientDTOPhone;
        this.patientDTOEmail = patientDTOEmail;
        this.cityId = cityId;
        this.patientDTOAddress = patientDTOAddress;
        this.patientDTOAddressNumber = patientDTOAddressNumber;
        this.patientDTOAddressFloor = patientDTOAddressFloor;
        this.patientDTOAddressApartment = patientDTOAddressApartment;
    }

    public PatientDTO(
                String patientDTOId,
                String patientDTODNI,
                String patientDTOName,
                String patientDTOLastName,
                String patientDTOBirthDate,
                String patientDTOPhone,
                String patientDTOEmail,
                String cityId,
                String patientDTOAddress,
                String patientDTOAddressNumber,
                String patientDTOAddressFloor,
                String patientDTOAddressApartment) {
        this.patientDTOId = patientDTOId;
        this.patientDTODNI = patientDTODNI;
        this.patientDTOName = patientDTOName;
        this.patientDTOLastName = patientDTOLastName;
        this.patientDTOBirthDate = patientDTOBirthDate;
        this.patientDTOPhone = patientDTOPhone;
        this.patientDTOEmail = patientDTOEmail;
        this.cityId = cityId;
        this.patientDTOAddress = patientDTOAddress;
        this.patientDTOAddressNumber = patientDTOAddressNumber;
        this.patientDTOAddressFloor = patientDTOAddressFloor;
        this.patientDTOAddressApartment = patientDTOAddressApartment;
    }

    public String getPatientDTOId() {
        return patientDTOId;
    }

    public void setPatientDTOId(String patientDTOId) {
        this.patientDTOId = patientDTOId;
    }

    public String getPatientDTODNI() {
            return patientDTODNI;
    }

    public void setPatientDTODNI(String patientDTODNI) {
            this.patientDTODNI = patientDTODNI;
    }

    public String getPatientDTOName() {
            return patientDTOName;
    }

    public void setPatientDTOName(String patientDTOName) {
            this.patientDTOName = patientDTOName;
    }

    public String getPatientDTOLastName() {
            return patientDTOLastName;
    }

    public void setPatientDTOLastName(String patientDTOLastName) {
            this.patientDTOLastName = patientDTOLastName;
    }

    public String getPatientDTOBirthDate() {
            return patientDTOBirthDate;
    }

    public void setPatientDTOBirthDate(String patientDTOBirthDate) {
            this.patientDTOBirthDate = patientDTOBirthDate;
    }

    public String getPatientDTOPhone() {
            return patientDTOPhone;
    }

    public void setPatientDTOPhone(String patientDTOPhone) {
            this.patientDTOPhone = patientDTOPhone;
    }

    public String getPatientDTOEmail() {
            return patientDTOEmail;
    }

    public void setPatientDTOEmail(String patientDTOEmail) {
            this.patientDTOEmail = patientDTOEmail;
    }

    public String getCityId() {
            return cityId;
    }

    public void setCityId(String cityId) {
            this.cityId = cityId;
    }

    public String getPatientDTOAddress() {
            return patientDTOAddress;
    }

    public void setPatientDTOAddress(String patientDTOAddress) {
            this.patientDTOAddress = patientDTOAddress;
    }

    public String getPatientDTOAddressNumber() {
            return patientDTOAddressNumber;
    }

    public void setPatientDTOAddressNumber(String patientDTOAddressNumber) {
            this.patientDTOAddressNumber = patientDTOAddressNumber;
    }

    public String getPatientDTOAddressFloor() {
            return patientDTOAddressFloor;
    }

    public void setPatientDTOAddressFloor(String patientDTOAddressFloor) {
            this.patientDTOAddressFloor = patientDTOAddressFloor;
    }

    public String getPatientDTOAddressApartment() {
            return patientDTOAddressApartment;
    }

    public void setPatientDTOAddressApartment(String patientDTOAddressApartment) {
            this.patientDTOAddressApartment = patientDTOAddressApartment;
    }

}
