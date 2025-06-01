package com.application.services;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.exceptions.runtimeExceptions.dataAccessException.EntityNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.application.model.dao.ConsultationPatientDAO;
import com.application.model.dto.ConsultationPatientDTO;
import com.application.model.entities.ConsultationPatient;

public class ConsultationPatientService {
    private final ConsultationPatientDAO consultationPatientDAO;

    public ConsultationPatientService() {
        this.consultationPatientDAO = new ConsultationPatientDAO();
    }
    
    /**
     * Inserta un nuevo paciente en una consulta existente en el sistema
     * @param consultationPatientDTO datos del paciente a insertar en la consulta
     * @throws ValidationException Si los datos no son válidos o la consulta ya existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void insertConsultationPatient(ConsultationPatientDTO consultationPatientDTO) throws ValidationException, BusinessException {
        try {
            
            validateConsultationPatientData(consultationPatientDTO);
            
            ConsultationPatient consultationPatient = createConsultationPatientFromDTO(consultationPatientDTO);
            consultationPatientDAO.insertConsultationPatient(consultationPatient);

        } catch (DataAccessException e) {
            throw new BusinessException("Error al guardar la consulta en el sistema", e);
        } catch (IllegalArgumentException e) {
           throw new ValidationException("Formato de fecha inválido");
        }
    }
        
    /**
     * Elimina al paciente de la consulta existente en el sistema
     * @param consultationId de la consulta
     * @param patientId del paciente a eliminar de la consulta
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void deleteConsultationPatient(String consultationId, String patientId) throws ValidationException, BusinessException {
        try {
            consultationPatientDAO.deleteConsultationPatient(UUID.fromString(consultationId), UUID.fromString(patientId));
        } catch (EntityNotFoundException e) {
            throw new ValidationException("No existe consulta con Id '" + consultationId + "'");
        } catch (DataAccessException e) {
            throw new BusinessException("Error de base de datos al eliminar la consulta", e);
        }
    }
    
    /**
     * Obtiene los pacientes de una consulta determinada
     * @param consultationId identificador de la consulta
     * @return lista de DTOs de pacientes para la consulta especificada
     * @throws BusinessException Si ocurre un error durante el proceso
     */
   public List<ConsultationPatientDTO> getPatientsByConsultationId(String consultationId) throws BusinessException {
        try {
            return consultationPatientDAO.getPatientsByConsultationId(UUID.fromString(consultationId))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new BusinessException("Error al listar pacientes para la consulta pedida", e);
        }
    }

    /**
     * Modifica el estado del pago a 'pagado' de un paciente para una consulta determinada
     * @param consultationId Identificador de la consulta
     * @param patientId Identificador del paciente a cambiar de estado de pago
     * @throws ValidationException Si los datos no son válidos o el paciente no existe
     * @throws BusinessException Si ocurre otro error de negocio
     */
    public void setConsultationPatientPaid(String consultationId, String patientId) throws ValidationException, BusinessException {
        try {
            consultationPatientDAO.setConsultationPatientPaid(UUID.fromString(consultationId), UUID.fromString(patientId));         
        } catch (EntityNotFoundException e) {
            throw new ValidationException("No existe paciente con Id '" + patientId + "'");
        } catch (DataAccessException e) {
            throw new BusinessException("Error de base de datos al actualizar paciente", e);
        }
    }
   
    /**
     * Valida los datos básicos de la consulta
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
    
    /**
     * Crea un objeto ConsultationPatient a partir de un ConsultationPatientDTO
     */
    private ConsultationPatient createConsultationPatientFromDTO(ConsultationPatientDTO consultationPatientDTO) {
        return new ConsultationPatient(
            UUID.fromString(consultationPatientDTO.getConsultationId()),
            UUID.fromString(consultationPatientDTO.getPatientId()),
            Double.valueOf(consultationPatientDTO.getConsultationAmount()),
            Boolean.getBoolean(consultationPatientDTO.getIsPaid()),
            consultationPatientDTO.getPatientNotePath()            
        );
    }
    
    /**
     * Crea un objeto ConsultationPatientDTO a partir de un ConsultationPatient
     */
    private ConsultationPatientDTO convertToDTO(ConsultationPatient cp) {
        ConsultationPatientDTO dto = new ConsultationPatientDTO();
        dto.setConsultationId(cp.getConsultationId().toString());
        dto.setPatientId(cp.getPatientId().toString());
        dto.setConsultationAmount(cp.getConsultationAmount().toString());
        dto.setIsPaid(cp.getIsPaid().toString());
        dto.setPatientNotePath(cp.getPatientNotePath());
        
        return dto;
    }
}