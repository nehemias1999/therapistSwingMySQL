package com.application.controllers.panels;

import com.application.controllers.entities.CityController;
import com.application.controllers.entities.PatientController;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.CityDTO;
import com.application.model.dto.PatientDTO;
import com.application.view.panels.patient.PatientsPanel;
import java.io.IOException;
import java.util.List;
import java.util.Collections;

public class PatientsFormController {
    
    private final PatientController patientController;
    private final CityController cityController;
    private final PatientsPanel patientsForm;
    
    public PatientsFormController(PatientController patientController, CityController cityController) {
        this.patientController = patientController;
        this.cityController = cityController;
        
        this.patientsForm = new PatientsPanel();
        this.patientsForm.setController(this);
    }
    
    public PatientsPanel getView() {
        return patientsForm;
    }
    
    /**
     * Obtiene todos los pacientes en el sistema
     * @return List PatientDTO
     */
    public List<PatientDTO> getAllPatients() {
        try {
            List<PatientDTO> patients = patientController.getAllPatients();
            return patients != null ? patients : Collections.emptyList();
        } catch (BusinessException e) {
            patientsForm.showErrorMessage("Error al obtener pacientes: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    
    /**
     * Obtiene todas las ciudades en el sistema
     * @return List CityDTO
     */
    public List<CityDTO> getAllCities() {
        try {
            List<CityDTO> cities = cityController.getAllCities();
            return cities != null ? cities : Collections.emptyList();
        } catch (BusinessException e) {
            patientsForm.showErrorMessage("Error al obtener ciudades: " + e.getMessage());
            return Collections.emptyList();
        }
    }
         
    /**
     * Inserta un nuevo paciente
     * @param patientDTO Datos del paciente a insertar
     */
    public void insertPatient(PatientDTO patientDTO) {
        try {
            patientController.insertPatient(patientDTO);
        } catch (ValidationException | BusinessException e) {
            patientsForm.showErrorMessage(e.getMessage());
        } catch (IOException e) {
            patientsForm.showErrorMessage("Error al guardar la foto del paciente: " + e.getMessage());
        }
    }
    
    /**
     * Modifica paciente existente
     * @param patientDTO Datos del paciente a modificar
     */
    public void updatePatient(PatientDTO patientDTO) {
        try {
            patientController.updatePatient(patientDTO);
        } catch (ValidationException | BusinessException e) {
            patientsForm.showErrorMessage(e.getMessage());
        } catch (IOException e) {
            patientsForm.showErrorMessage("Error al actualizar la foto del paciente: " + e.getMessage());
        }
    }
    
    /**
     * Elimina paciente existente
     * @param patientId del paciente a eliminar
     */
    public void deletePatient(String patientId) {
        try {
            patientController.deletePatient(patientId);
        } catch (ValidationException | BusinessException e) {
            patientsForm.showErrorMessage(e.getMessage());
        }
    }
    
    /**
     * Obtiene el paciente en base a su Id
     * @param patientId
     * @return PatientDTO
     */
    public PatientDTO getPatientById(String patientId) {
        try {
            return patientController.getPatientById(patientId);
        } catch (ValidationException | BusinessException e) {
            patientsForm.showErrorMessage(e.getMessage());
            return null;
        }
    } 
    
    /**
     * Obtiene el nombre de la ciudad en base a su Id
     * @param cityId
     * @return String City name
     */
    public String getCityNameById(String cityId) {
        try {
            return cityController.getCityNameById(cityId);
        } catch (ValidationException | BusinessException e) {
            patientsForm.showErrorMessage(e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca pacientes en base a su apellido
     * @param patientData Search term
     * @return List PatientDTO 
     */
    public List<PatientDTO> getPatientsThatMatch(String patientData) {
        try {
            List<PatientDTO> result = patientController.getPatientsThatMatch(patientData);
            return result != null ? result : Collections.emptyList();
        } catch (BusinessException e) {
            patientsForm.showErrorMessage("Error en búsqueda: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}