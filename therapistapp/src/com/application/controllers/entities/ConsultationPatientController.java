package com.application.controllers.entities;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.ConsultationPatientDTO;
import com.application.model.dto.PatientDTO;

import java.util.List;

import com.application.services.ConsultationPatientService;
import java.io.IOException;

public class ConsultationPatientController {
    private final ConsultationPatientService consultationPatientService;
    
    public ConsultationPatientController(ConsultationPatientService consultationPatientService) {
        this.consultationPatientService = consultationPatientService;
    }
    
    /**
     * Inserta una lista de pacientes en una consulta existente en el sistema
     * @param consultationId Identificador de la consulta
     * @param consultationPatientsDTO Datos de los pacientes
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void insertConsultationPatients(String consultationId, List<PatientDTO> consultationPatientsDTO) throws ValidationException, BusinessException {
        consultationPatientService.insertConsultationPatients(consultationId, consultationPatientsDTO);
    }
    
    /**
     * Modifica una lista de pacientes en una consulta existente en el sistema
     * @param consultationId Identificador de la consulta
     * @param consultationPatientsDTO Datos de los pacientes
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void updateConsultationPatients(String consultationId, List<PatientDTO> consultationPatientsDTO) throws ValidationException, BusinessException {
        consultationPatientService.updateConsultationPatients(consultationId, consultationPatientsDTO);
    }
    
    /**
     * Elimina al paciente de la consulta existente en el sistema
     * @param consultationId Identificador de la consulta
     * @param patientId Identificador del paciente
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void deleteConsultationPatient(String consultationId, String patientId) throws ValidationException, BusinessException {
        if (consultationId == null || consultationId.trim().isEmpty()) {
            throw new ValidationException("El Identificador de la consulta es requerido");
        }
        if (patientId == null || patientId.trim().isEmpty()) {
            throw new ValidationException("El Identificador del paciente es requerido");
        }
        consultationPatientService.deleteConsultationPatient(consultationId, patientId);
    }
    
    /**
     * Obtiene los pacientes de una consulta determinada
     * @param consultationId Identificador de la consulta
     * @return Lista de PatientDTO
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public List<PatientDTO> getPatientsByConsultationId(String consultationId) throws ValidationException, BusinessException {
        if (consultationId == null || consultationId.trim().isEmpty()) {
            throw new ValidationException("El Identificador de la consulta es requerido");
        }
        
        List<PatientDTO> consultationPatientsDTO = consultationPatientService.getPatientsByConsultationId(consultationId);
        
        for(PatientDTO patientDTO: consultationPatientsDTO) {
            
            patientDTO.setPaid(
                consultationPatientService.isConsultationPatientPaid(consultationId, patientDTO.getPatientDTOId())
            );
        }
        
        return consultationPatientsDTO;
    }
    
    /**
     * Verifica si el estado del pago de la consulta es pago o no
     * @param consultationId Identificador de la consulta
     * @param patientId Identificador del paciente
     * @return Boolean Si el estado es pago o no
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public Boolean isConsultationPatientPaid(String consultationId, String patientId) throws ValidationException, BusinessException {
        if (consultationId == null || consultationId.trim().isEmpty()) {
            throw new ValidationException("El Identificador de la consulta es requerido");
        }
        if (patientId == null || patientId.trim().isEmpty()) {
            throw new ValidationException("El Identificador del paciente es requerido");
        }
        return consultationPatientService.isConsultationPatientPaid(consultationId, patientId);
    }
    
    /**
     * Modifica el estado del pago de consulta para el paciente indicado
     * @param consultationId Identificador de la consulta
     * @param patientId Identificador del paciente
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void setConsultationPatientPaid(String consultationId, String patientId) throws ValidationException, BusinessException {
        if (consultationId == null || consultationId.trim().isEmpty()) {
            throw new ValidationException("El Identificador de la consulta es requerido");
        }
        if (patientId == null || patientId.trim().isEmpty()) {
            throw new ValidationException("El Identificador del paciente es requerido");
        }
        consultationPatientService.setConsultationPatientPaid(consultationId, patientId);
    }

    /**
     * Valida los datos del paciente de la consulta
     * @param consultationPatientDTO datos de; paciente de la consulta a validar
     * @throws ValidationException si la validacion falla
     */
    private void validateBasicFields(ConsultationPatientDTO consultationPatientDTO) throws ValidationException {
        if (consultationPatientDTO == null) {
            throw new ValidationException("Los datos son requeridos");
        }
        if (consultationPatientDTO.getIsPaid()== null || consultationPatientDTO.getIsPaid().trim().isEmpty()) {
            throw new ValidationException("El estado del pago de la consulta es requerido");
        }
    }
}