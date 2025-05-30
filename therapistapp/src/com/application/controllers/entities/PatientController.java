package com.application.controllers.entities;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.PatientDTO;
import com.application.services.PatientService;
import java.io.IOException;

import java.util.List;

public class PatientController {
    private final PatientService patientService;
    
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
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
     * Inserta un nuevo paciente
     * @param patientDTO Datos del paciente a insertar
     * @throws ValidationException Si los datos no son válidos o el paciente ya existe
     * @throws BusinessException Si ocurre otro error de negocio
     * @throws java.io.IOException
     */
    public void insertPatient(PatientDTO patientDTO) throws ValidationException, BusinessException, IOException {
        validatePatientData(patientDTO);
        patientService.insertPatient(patientDTO);
    }
    
    /**
     * Modifica paciente existente
     * @param patientDTO Datos del paciente a modificar
     * @throws ValidationException Si los datos no son válidos
     * @throws BusinessException Si ocurre otro error de negocio
     * @throws java.io.IOException
     */
    public void updatePatient(PatientDTO patientDTO) throws ValidationException, BusinessException, IOException {
        validatePatientData(patientDTO);
        patientService.updatePatient(patientDTO);
    }
    
    /**
     * Elimina paciente existente
     * @param patientId del paciente a eliminar
     * @throws ValidationException Si los datos no son válidos o el paciente ya existe
     * @throws BusinessException Si ocurre otro error de negocio
     */
    public void deletePatient(String patientId) throws ValidationException, BusinessException {
        if (patientId == null) {
            throw new ValidationException("El Id del paciente es requerido");
        }
        
        patientService.deletePatient(patientId);
    }

    /**
     * Obtiene el paciente en base a su Id
     * @param patientId del paciente a buscar
     * @return PatientDTO si lo encuentra
     * @throws ValidationException Si los datos no son válidos o el paciente ya existe
     * @throws BusinessException Si ocurre otro error de negocio
     */
    public PatientDTO getPatientById(String patientId) throws ValidationException, BusinessException {
        return patientService.getPatientById(patientId);
    } 
    
    /**
     * Busca pacientes en base a su nombre
     * @param searchTerm Search term
     * @return Lista de pacientes que coincidan
     * @throws BusinessException  Si ocurre otro error de negocio
     */
    public List<PatientDTO> searchPatientsByName(String searchTerm) throws BusinessException {
        String term = searchTerm.toLowerCase().trim();
        return patientService.getAllPatients().stream()
                .filter(p -> p.getPatientDTOName().toLowerCase().contains(term) || 
                           p.getPatientDTOLastName().toLowerCase().contains(term))
                .toList();
    }
    
    /**
     * Valida los datos del paciente
     * @param patientDTO datos del paciente a validar
     * @throws ValidationException si la validacion falla
     */
    private void validatePatientData(PatientDTO dto) throws ValidationException {

        if (dto.getPatientDTODNI() == null || dto.getPatientDTODNI().trim().isEmpty()) {
            throw new ValidationException("El DNI del paciente es requerido");
        }
        
        if (dto.getPatientDTOName() == null || dto.getPatientDTOName().trim().isEmpty()) {
            throw new ValidationException("El nombre del paciente es requerido");
        }
        
        if (dto.getPatientDTOLastName() == null || dto.getPatientDTOLastName().trim().isEmpty()) {
            throw new ValidationException("El apellido del paciente es requerido");
        }
        
        if (dto.getPatientDTOBirthDate() == null || dto.getPatientDTOBirthDate().trim().isEmpty()) {
            throw new ValidationException("La fecha de nacimiento es requerida");
        }
                
        if (dto.getPatientDTOPhone() == null || dto.getPatientDTOPhone().trim().isEmpty()) {
            throw new ValidationException("El numero de celular del paciente es requerido");
        }
        
        if (dto.getPatientDTOEmail() == null || dto.getPatientDTOEmail().trim().isEmpty()) {
            throw new ValidationException("E-mail inválido");
        }
        
        if (dto.getCityId() == null || dto.getCityId().trim().isEmpty()) {
            throw new ValidationException("La ciudad es requerida");
        }

        if (dto.getPatientDTOAddress() == null || dto.getPatientDTOAddress().trim().isEmpty()) {
            throw new ValidationException("La dirección del paciente es requerida");
        }
        
        if (dto.getPatientDTOAddressNumber() == null || dto.getPatientDTOAddressNumber().trim().isEmpty()) {
            throw new ValidationException("El numero de la direccion del paciente es requerido");
        }
    }
}
