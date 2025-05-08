package com.application.controllers.panels;

import com.application.controllers.entities.CityController;
import com.application.controllers.entities.PatientController;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.CityDTO;
import com.application.model.dto.PatientDTO;
import com.application.view.menu.panels.patient.PatientsForm;
import java.util.List;
import java.util.Collections;

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
            patientsForm.showErrorMessage("Error al cargar pacientes: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    
    public List<CityDTO> getAllCities() {
        try {
            List<CityDTO> cities = cityController.getAllCities();
            return cities != null ? cities : Collections.emptyList();
        } catch (BusinessException e) {
            patientsForm.showErrorMessage("Error al cargar ciudades: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    
    public void searchPatients(String query) {
//        try {
//            List<PatientDTO> results = patientService.searchPatients(query);
//            patientsForm.displayPatients(results);
//        } catch (BusinessException e) {
//            patientsForm.showErrorMessage("Error en búsqueda: " + e.getMessage());
//        }
    }
    
    public void insertPatient(PatientDTO patientDTO) throws ValidationException, BusinessException {
        try {
            
            // Validación básica antes de enviar al servicio
            if (patientDTO == null) {
                throw new ValidationException("Datos del paciente no pueden ser nulos");
            }
            
            patientController.insertPatient(patientDTO);
            //patientsForm.showSuccessMessage("Paciente agregado exitosamente");
            
            //getAllPatients(); 
            
        } catch (ValidationException e) {
            patientsForm.showErrorMessage(e.getMessage());
        } catch (BusinessException e) {
            patientsForm.showErrorMessage("Error al guardar paciente: " + e.getMessage());
        }
    }
    
    public void updatePatient(PatientDTO patientDTO) {
//        try {
//            if (patientDTO == null || patientDTO.getId() == null) {
//                throw new ValidationException("Datos inválidos para actualización");
//            }
//            
//            patientService.updatePatient(patientDTO);
//            patientsForm.showSuccessMessage("Paciente actualizado exitosamente");
//            loadPatients(); // Refrescar la lista
//            
//        } catch (ValidationException e) {
//            patientsForm.showValidationError(e.getMessage());
//        } catch (BusinessException e) {
//            patientsForm.showErrorMessage("Error al actualizar paciente: " + e.getMessage());
//        }
    }
    
    public void deletePatient(String patientId) {
//        try {
//            if (patientId == null || patientId.isEmpty()) {
//                throw new ValidationException("ID de paciente inválido");
//            }
//            
//            if (patientsForm.confirmAction("¿Está seguro de eliminar este paciente?")) {
//                patientService.deletePatient(patientId);
//                patientsForm.showSuccessMessage("Paciente eliminado exitosamente");
//                loadPatients(); // Refrescar la lista
//            }
//            
//        } catch (BusinessException e) {
//            patientsForm.showErrorMessage("Error al eliminar paciente: " + e.getMessage());
//        }
    }
}