package com.application.controllers.panels;

import com.application.controllers.entities.CityController;
import com.application.controllers.entities.PatientController;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.CityDTO;
import com.application.model.dto.PatientDTO;
import com.application.view.panels.patient.PatientsForm;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientsFormController {
    
    private final PatientController patientController;
    private final CityController cityController;
    private final PatientsForm patientsForm;
    
    public PatientsFormController(PatientController patientController, CityController cityController) {
        this.patientController = patientController;
        this.cityController = cityController;
        
        this.patientsForm = new PatientsForm();
        this.patientsForm.setController(this);
    }
    
    public PatientsForm getView() {
        return patientsForm;
    }
    
    public List<PatientDTO> getAllPatients() {
        try {
            List<PatientDTO> patients = patientController.getAllPatients();
            return patients != null ? patients : Collections.emptyList();
        } catch (BusinessException e) {
            patientsForm.showErrorMessage(e.getMessage());
            return Collections.emptyList();
        }
    }
    
    public List<CityDTO> getAllCities() {
        try {
            List<CityDTO> cities = cityController.getAllCities();
            return cities != null ? cities : Collections.emptyList();
        } catch (BusinessException e) {
            patientsForm.showErrorMessage(e.getMessage());
            return Collections.emptyList();
        }
    }
    
    public String getCityNameById(String cityId) {
        try {
            return cityController.getCityNameById(cityId);
        } catch (BusinessException e) {
            patientsForm.showErrorMessage(e.getMessage());
            return null;
        } catch (ValidationException e) {
            patientsForm.showErrorMessage(e.getMessage());
            return null;
        }
    }
        
    public void insertPatient(PatientDTO patientDTO) throws ValidationException, BusinessException, IOException {
        patientController.insertPatient(patientDTO);
    }
    
    public void updatePatient(PatientDTO patientDTO) throws ValidationException, BusinessException, IOException {
        patientController.updatePatient(patientDTO);
    }
    
    public void deletePatient(String patientId) throws ValidationException, BusinessException {
        patientController.deletePatient(patientId);
    }
    
    /**
     * Gets a patient by their Id
     * @param patientId
     * @return PatientDTO if found
     * @throws BusinessException If there's an error accessing data
     * @throws ValidationException If patient is not found
     */
    public PatientDTO getPatientById(String patientId) throws BusinessException, ValidationException {
        return patientController.getPatientById(patientId);
    } 
    
    public void searchPatients(String query) {
//        try {
//            List<PatientDTO> results = patientService.searchPatients(query);
//            patientsForm.displayPatients(results);
//        } catch (BusinessException e) {
//            patientsForm.showErrorMessage("Error en búsqueda: " + e.getMessage());
//        }
    }
}