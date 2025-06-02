package com.application.controllers.entities;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.ConsultationPatientDTO;
import com.application.model.dto.PatientDTO;

import java.util.List;

import com.application.services.ConsultationPatientService;

public class ConsultationPatientController {
    private final ConsultationPatientService consultationPatientService;
    
    public ConsultationPatientController(ConsultationPatientService consultationPatientService) {
        this.consultationPatientService = consultationPatientService;
    }
    
    /**
     * Inserta un nuevo paciente en una consulta existente en el sistema
     * @param consultationPatientDTO Datos del paciente a insertar
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void insertConsultationPatient(ConsultationPatientDTO consultationPatientDTO) throws ValidationException, BusinessException {
        validateConsultationPatientData(consultationPatientDTO);
        consultationPatientService.insertConsultationPatient(consultationPatientDTO);
    }
    
    /**
     * Elimina al paciente de la consulta existente en el sistema
     * @param consultationId Identificador de la consulta
     * @param patientId Identificador del paciente
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void deleteConsultationPatient(String consultationId, String patientId) throws ValidationException, BusinessException {
        if (consultationId == null) {
            throw new ValidationException("El identificador de la consulta es requerido");
        }
        if (patientId == null) {
            throw new ValidationException("El identificador del paciente es requerido");
        }
        
        consultationPatientService.deleteConsultationPatient(consultationId, patientId);
    }
    
    /**
     * Obtiene los pacientes de una consulta determinada
     * @param consultationId Identificador de la consulta a buscar pacientes
     * @return lista de DTOs de pacientes de la consulta 
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public List<PatientDTO> getPatientsByConsultationId(String consultationId) throws BusinessException {
        return consultationPatientService.getPatientsByConsultationId(consultationId).stream().toList();
    }
    
    /**
     * Modifica el estado del pago de consulta para el paciente indicado
     * @param consultationId Identificador de la consulta
     * @param patientId Identificador del paciente
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void setConsultationPatientPaid(String consultationId, String patientId) throws ValidationException, BusinessException {
        if (consultationId == null) {
            throw new ValidationException("El identificador de la consulta es requerido");
        }
        if (patientId == null) {
            throw new ValidationException("El identificador del paciente es requerido");
        }
        
        consultationPatientService.setConsultationPatientPaid(consultationId, patientId);
    }

    /**
     * Valida los datos del paciente de la consulta
     * @param consultationPatientDTO datos de; paciente de la consulta a validar
     * @throws ValidationException si la validacion falla
     */
    private void validateConsultationPatientData(ConsultationPatientDTO consultationPatientDTO) throws ValidationException {

        if (consultationPatientDTO.getConsultationAmount() == null || consultationPatientDTO.getConsultationAmount().trim().isEmpty()) {
            throw new ValidationException("El monto de la consulta es requerido");
        }

        if (consultationPatientDTO.getIsPaid()== null || consultationPatientDTO.getIsPaid().trim().isEmpty()) {
            throw new ValidationException("El estado del pago de la consulta es requerido");
        }

        if (consultationPatientDTO.getPatientNotePath() == null || consultationPatientDTO.getPatientNotePath().trim().isEmpty()) {
            throw new ValidationException("El path de las notas de paciente para la consulta es requerido");
        }
    }
}