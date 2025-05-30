package com.application.services;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.exceptions.runtimeExceptions.dataAccessException.EntityNotFoundException;
import com.application.model.dao.ConsultationDAO;
import com.application.model.dto.ConsultationDTO;
import com.application.model.entities.Consultation;
import com.application.model.enumerations.ConsultationStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConsultationService {
    private final ConsultationDAO consultationDAO;

    public ConsultationService() {
        this.consultationDAO = new ConsultationDAO();
    }
    
    /**
     * Inserta una nueva consulta en el sistema
     * @param consultationDTO Datos de la consulta a insertar
     * @throws ValidationException Si los datos no son válidos o la consulta ya existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void insertConsultation(ConsultationDTO consultationDTO) throws ValidationException, BusinessException {
        try {
            
            validateConsultationData(consultationDTO);
            
            Consultation consultation = createConsultationFromDTO(consultationDTO);
            consultationDAO.insertConsultation(consultation);

        } catch (DataAccessException e) {
            throw new BusinessException("Error al guardar la consulta en el sistema", e);
        } catch (IllegalArgumentException e) {
           throw new ValidationException("Formato de fecha inválido");
        }
    }
    
    /**
     * Modifica una consulta existente en el sistema
     * @param consultationDTO Datos de la consulta a modificar
     * @throws ValidationException Si los datos no son válidos o la consulta ya existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void updateConsultation(ConsultationDTO consultationDTO) throws ValidationException, BusinessException {
        try {
            
            validateConsultationData(consultationDTO);
            
            Consultation consultation = createConsultationFromDTO(consultationDTO);
            consultationDAO.updateConsultation(consultation);

        } catch (DataAccessException e) {
            throw new BusinessException("Error al guardar la consulta en el sistema", e);
        } catch (IllegalArgumentException e) {
           throw new ValidationException("Formato de fecha inválido");
        }
    }
    
    /**
     * Elimina una consulta existente en el sistema
     * @param consultationId de la consulta a eliminar
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void deleteConsultation(String consultationId) throws ValidationException, BusinessException {
        try {
            consultationDAO.deleteConsultation(UUID.fromString(consultationId));
        } catch (EntityNotFoundException e) {
            throw new ValidationException("No existe consulta con Id '" + consultationId + "'");
        } catch (DataAccessException e) {
            throw new BusinessException("Error de base de datos al eliminar la consulta", e);
        }
    }
    
    /**
     * Obtiene las consulta para un dia determinado
     * @param consultationDate fecha de las consultas a buscar
     * @return lista de DTOs de consulta para la fecha especificada
     * @throws BusinessException Si ocurre un error durante el proceso
     */
   public List<ConsultationDTO> getConsultationsByDate(String consultationDate) throws BusinessException {
        try {
            
            java.sql.Date sqlDate = java.sql.Date.valueOf(consultationDate);

            return consultationDAO
                    .getConsultationsByDate(sqlDate)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

        } catch (DateTimeParseException e) {
            throw new BusinessException("Formato de fecha inválido, debe ser yyyy-MM-dd", e);
        } catch (DataAccessException e) {
            throw new BusinessException("Error al listar consultas por fecha", e);
        }
    }

    /**
     * Valida los datos básicos de la consulta
     */
    private void validateConsultationData(ConsultationDTO consultationDTO) throws ValidationException {

        if (consultationDTO.getConsultationDTOStartDateTime() == null || consultationDTO.getConsultationDTOStartDateTime().trim().isEmpty()) {
            throw new ValidationException("La fecha/hora de inicio es requerida");
        }

        if (consultationDTO.getConsultationDTOEndDateTime() == null || consultationDTO.getConsultationDTOEndDateTime().trim().isEmpty()) {
            throw new ValidationException("La fecha/hora de fin es requerida");
        }

        if (consultationDTO.getConsultationDTOStatus() == null || consultationDTO.getConsultationDTOStatus().trim().isEmpty()) {
            throw new ValidationException("El estado de la consulta es requerido");
        }
        
        LocalDateTime startDateTime = LocalDateTime.parse(consultationDTO.getConsultationDTOStartDateTime());
        LocalDateTime endDateTime = LocalDateTime.parse(consultationDTO.getConsultationDTOEndDateTime());

        if (startDateTime.isAfter(endDateTime)) {
            throw new ValidationException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        if (consultationDAO.isConsultationStartDatetimeExists(startDateTime)) {
            throw new ValidationException("Ya existe una consulta programada para la fecha/hora: " + startDateTime);
        }
    }
    
    /**
     * Crea un objeto Consultation a partir de un ConsultationDTO
     */
    private Consultation createConsultationFromDTO(ConsultationDTO consultationDTO) {
        UUID consultationId = Optional.ofNullable(consultationDTO.getConsultationDTOId())
                                .filter(s -> !s.isBlank())
                                .map(UUID::fromString)
                                .orElseGet(UUID::randomUUID);
        
        return new Consultation(
            consultationId,
            LocalDateTime.parse(consultationDTO.getConsultationDTOStartDateTime()),
            LocalDateTime.parse(consultationDTO.getConsultationDTOEndDateTime()),
            ConsultationStatus.valueOf(consultationDTO.getConsultationDTOStatus())
        );
    }
    
    private ConsultationDTO convertToDTO(Consultation c) {
        ConsultationDTO dto = new ConsultationDTO();
        dto.setConsultationDTOId(c.getConsultationId().toString());
        dto.setConsultationDTOStartDateTime(c.getConsultationStartDateTime().toString());
        dto.setConsultationDTOEndDateTime(c.getConsultationEndDateTime().toString());
        dto.setConsultationDTOStatus(c.getConsultationStatus().toString());
        
        return dto;
    }
}