package com.application.interfaces;

import com.application.model.dto.CityDTO;
import com.application.model.dto.PatientDTO;
import java.util.List;

public interface IPatientDialogListener {
    PatientDTO getPatientById(String patientId); 
    List<CityDTO> getAllCities(); 
    void insertPatient(PatientDTO patientDTO);
    void updatePatient(PatientDTO patientDTO);
}

