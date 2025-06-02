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
    
    /**
     * Obtiene todos los pacientes en el sistema
     * @return lista de PatientDTO de los pacientes encontrados
     * @throws BusinessException Si hubo un error acediendo a los datos
     */
    public List<PatientDTO> getAllPatients() {
        try {
            List<PatientDTO> patients = patientController.getAllPatients();
            return patients != null ? patients : Collections.emptyList();
        } catch (BusinessException e) {
            patientsForm.showErrorMessage(e.getMessage());
            return Collections.emptyList();
        }
    }
    
    /**
     * Obtiene todas las ciudades en el sistema
     * @return lista de CityDTO de las ciudades encontrados
     * @throws BusinessException Si hubo un error acediendo a los datos
     */
    public List<CityDTO> getAllCities() {
        try {
            List<CityDTO> cities = cityController.getAllCities();
            return cities != null ? cities : Collections.emptyList();
        } catch (BusinessException e) {
            patientsForm.showErrorMessage(e.getMessage());
            return Collections.emptyList();
        }
    }
         
    /**
     * Inserta un nuevo paciente
     * @param patientDTO Datos del paciente a insertar
     * @throws ValidationException Si los datos no son válidos o el paciente ya existe
     * @throws BusinessException Si ocurre otro error de negocio
     * @throws java.io.IOException
     */
    public void insertPatient(PatientDTO patientDTO) throws ValidationException, BusinessException, IOException {
        patientController.insertPatient(patientDTO);
    }
    
    /**
     * Modifica paciente existente
     * @param patientDTO Datos del paciente a modificar
     * @throws ValidationException Si los datos no son válidos
     * @throws BusinessException Si ocurre otro error de negocio
     * @throws java.io.IOException
     */
    public void updatePatient(PatientDTO patientDTO) throws ValidationException, BusinessException, IOException {
        patientController.updatePatient(patientDTO);
    }
    
    /**
     * Elimina paciente existente
     * @param patientId del paciente a eliminar
     * @throws ValidationException Si los datos no son válidos o el paciente ya existe
     * @throws BusinessException Si ocurre otro error de negocio
     */
    public void deletePatient(String patientId) throws ValidationException, BusinessException {
        patientController.deletePatient(patientId);
    }
    
    /**
     * Obtiene el paciente en base a su Id
     * @param patientId
     * @return PatientDTO del paciente a buscar
     * @throws BusinessException Si hubo un error acediendo a los datos
     * @throws ValidationException Si la ciudad no fue encontrada
     */
    public PatientDTO getPatientById(String patientId) throws BusinessException, ValidationException {
        return patientController.getPatientById(patientId);
    } 
    
    /**
     * Obtiene el nombre de la ciudad en base a su Id
     * @param cityId
     * @return Nombre de la ciudad a buscar
     * @throws BusinessException Si hubo un error acediendo a los datos
     * @throws ValidationException Si la ciudad no fue encontrada
     */
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
    
    /**
     * Busca pacientes en base a su apellido
     * @param patientLastName Search term
     * @return Lista de pacientes que coincidan
     * @throws BusinessException  Si ocurre otro error de negocio
     */
    public List<PatientDTO> getPatientsThatMatch(String patientData) {
        try {
            return patientController.getPatientsThatMatch(patientData);
        } catch (BusinessException e) {
            patientsForm.showErrorMessage("Error en búsqueda: " + e.getMessage());
            return null;
        }
    }
}