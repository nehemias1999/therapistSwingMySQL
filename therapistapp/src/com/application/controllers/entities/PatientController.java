package com.application.controllers.entities;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.PatientDTO;
import com.application.services.PatientService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PatientController {
    private final PatientService patientService;
    
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    
    public void insertPatient(PatientDTO patientDTO) throws ValidationException, BusinessException {
    
        // Validaciones basicas
        
        patientService.insertPatient(patientDTO);
    }

    /**
     * Retrieves all patients from the system
     * @return List of PatientDTO objects
     * @throws BusinessException If there's an error accessing data
     */
    public List<PatientDTO> getAllPatients() throws BusinessException {
        return patientService.getAllPatients();
    }

    /**
     * Registers a new patient in the system
     * @param patientDTO Patient data to register
     * @throws ValidationException If input data is invalid
     * @throws BusinessException If there's a business error
     */
    public void registerPatient(PatientDTO patientDTO) throws ValidationException, BusinessException {
        patientService.insertPatient(patientDTO);
    }

    /**
     * Validates patient data before registration
     * @param patientDTO Patient data to validate
     * @throws ValidationException If validation fails
     */
    public void validatePatientData(PatientDTO patientDTO) throws ValidationException {
        if (patientDTO.getPatientDTODNI() == null || patientDTO.getPatientDTODNI().trim().isEmpty()) {
            throw new ValidationException("El DNI del paciente es requerido");
        }
        // Additional validations are handled by the service layer
    }

    /**
     * Checks if a patient with given DNI already exists
     * @param dni Patient DNI to check
     * @return true if exists, false otherwise
     * @throws BusinessException If there's an error checking
     */
    public boolean patientExistsByDNI(String dni) throws BusinessException {
        try {
            return patientService.getAllPatients().stream()
                    .anyMatch(p -> p.getPatientDTODNI().equalsIgnoreCase(dni.trim()));
        } catch (BusinessException e) {
            throw new BusinessException("Error al verificar existencia de paciente", e);
        }
    }

    /**
     * Searches patients by name (partial match)
     * @param searchTerm Search term
     * @return List of matching patients
     * @throws BusinessException If there's a search error
     */
    public List<PatientDTO> searchPatientsByName(String searchTerm) throws BusinessException {
        String term = searchTerm.toLowerCase().trim();
        return patientService.getAllPatients().stream()
                .filter(p -> p.getPatientDTOName().toLowerCase().contains(term) || 
                           p.getPatientDTOLastName().toLowerCase().contains(term))
                .toList();
    }

    /**
     * Gets a patient by their DNI
     * @param dni Patient DNI
     * @return PatientDTO if found
     * @throws BusinessException If there's an error accessing data
     * @throws ValidationException If patient is not found
     */
    public PatientDTO getPatientByDNI(String dni) throws BusinessException, ValidationException {
        return patientService.getAllPatients().stream()
                .filter(p -> p.getPatientDTODNI().equalsIgnoreCase(dni.trim()))
                .findFirst()
                .orElseThrow(() -> new ValidationException("No se encontró paciente con DNI: " + dni));
    }
}
