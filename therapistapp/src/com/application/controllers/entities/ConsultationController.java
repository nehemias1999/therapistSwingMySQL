package com.application.controllers.entities;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.ConsultationDTO;
import com.application.services.ConsultationService;

import java.util.List;

public class ConsultationController {
    private final ConsultationService consultationService;
    
    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }
    
    /**
     * Inserta una nueva consulta en el sistema
     * @param consultationDTO Datos de la consulta a insertar
     * @throws ValidationException Si los datos no son válidos o la consulta ya existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void insertConsultation(ConsultationDTO consultationDTO) throws ValidationException, BusinessException {
        validateConsultationData(consultationDTO);
        consultationService.insertConsultation(consultationDTO);
    }
    
    /**
     * Modifica una consulta existente en el sistema
     * @param consultationDTO Datos de la consulta a modificar
     * @throws ValidationException Si los datos no son válidos o la consulta ya existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void updateConsultation(ConsultationDTO consultationDTO) throws ValidationException, BusinessException {
        validateConsultationData(consultationDTO);
        consultationService.updateConsultation(consultationDTO);
    }
    
    /**
     * Elimina una consulta existente en el sistema
     * @param consultationId de la consulta a eliminar
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void deleteConsultation(String consultationId) throws ValidationException, BusinessException {
        if (consultationId == null) {
            throw new ValidationException("El Id de la consulta es requerida");
        }
        
        consultationService.deleteConsultation(consultationId);
    }
    
    /**
     * Obtiene las consulta para un dia determinado
     * @param consultationDate fecha de las consultas a buscar
     * @return lista de DTOs de consulta para la fecha especificada
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public List<ConsultationDTO> getConsultationsByDate(String consultationDate) throws BusinessException {
        return consultationService.getConsultationsByDate(consultationDate).stream().toList();
    }
    
    /**
     * Obtiene la consulta para un identificador determinado
     * @param consultationId Identificador de la consulta a buscar
     * @return DTO de la consulta 
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public ConsultationDTO getConsultationById(String consultationId) throws BusinessException {
        return consultationService.getConsultationById(consultationId);
    }

    /**
     * Valida los datos de la consulta
     * @param consultationDTO datos de la consulta a validar
     * @throws ValidationException si la validacion falla
     */
    public void validateConsultationData(ConsultationDTO consultationDTO) throws ValidationException {
        
        if (consultationDTO.getConsultationDTOStartDateTime() == null || consultationDTO.getConsultationDTOStartDateTime().trim().isEmpty()) {
            throw new ValidationException("La fecha de inicio de la consulta es requerida");
        }
        
        if (consultationDTO.getConsultationDTOEndDateTime() == null || consultationDTO.getConsultationDTOEndDateTime().trim().isEmpty()) {
            throw new ValidationException("La fecha de fin de la consulta es requerida");
        }

        if (consultationDTO.getConsultationDTOStatus() == null || consultationDTO.getConsultationDTOStatus().trim().isEmpty()) {
            throw new ValidationException("El estado de la consulta es requerido");
        }
    }
}