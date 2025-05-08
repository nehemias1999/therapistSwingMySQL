package com.application.services;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.model.dao.ConsultationDAO;
import com.application.model.dto.ConsultationDTO;
import com.application.model.entities.Consultation;
import com.application.model.enumerations.ConsultationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
        // Validaciones básicas de los datos de entrada
        validateConsultationData(consultationDTO);

        try {
            // Convertir y validar fechas
            LocalDateTime startDateTime = LocalDateTime.parse(consultationDTO.getConsultationDTOStartDateTime());
            LocalDateTime endDateTime = LocalDateTime.parse(consultationDTO.getConsultationDTOEndDateTime());

            // Validar que la fecha de inicio sea anterior a la de fin
            if (startDateTime.isAfter(endDateTime)) {
                throw new ValidationException("La fecha de inicio debe ser anterior a la fecha de fin");
            }

            // Verificar disponibilidad del horario
            if (consultationDAO.isConsultationStartDatetimeExists(startDateTime)) {
                throw new ValidationException("Ya existe una consulta programada para la fecha/hora: " + startDateTime);
            }

            // Crear y guardar la nueva consulta
            Consultation consultation = createConsultationFromDTO(consultationDTO);
            consultationDAO.insertConsultation(consultation);

        } catch (DataAccessException e) {
            throw new BusinessException("Error al guardar la consulta en el sistema", e);
        } catch (IllegalArgumentException e) {
           throw new ValidationException("Formato de fecha inválido");
        }
    }

    /**
     * Valida los datos básicos de la consulta
     */
    private void validateConsultationData(ConsultationDTO consultationDTO) throws ValidationException {
        if (consultationDTO.getPatientId() == null || consultationDTO.getPatientId().trim().isEmpty()) {
            throw new ValidationException("El ID del paciente es requerido");
        }

        if (consultationDTO.getConsultationDTOStartDateTime() == null || consultationDTO.getConsultationDTOStartDateTime().trim().isEmpty()) {
            throw new ValidationException("La fecha/hora de inicio es requerida");
        }

        if (consultationDTO.getConsultationDTOEndDateTime() == null || consultationDTO.getConsultationDTOEndDateTime().trim().isEmpty()) {
            throw new ValidationException("La fecha/hora de fin es requerida");
        }

        if (consultationDTO.getConsultationDTOStatus() == null || consultationDTO.getConsultationDTOStatus().trim().isEmpty()) {
            throw new ValidationException("El estado de la consulta es requerido");
        }

        if (consultationDTO.getConsultationDTOAmount() == null || consultationDTO.getConsultationDTOAmount().trim().isEmpty()) {
            throw new ValidationException("El monto de la consulta es requerido");
        }

        if (consultationDTO.getConsultationDTOAmountPaid() == null || consultationDTO.getConsultationDTOAmountPaid().trim().isEmpty()) {
            throw new ValidationException("El estado de pago es requerido");
        }
    }

    /**
     * Crea un objeto Consultation a partir de un ConsultationDTO
     */
    private Consultation createConsultationFromDTO(ConsultationDTO consultationDTO) {
        return new Consultation(
            UUID.randomUUID(),
            UUID.fromString(consultationDTO.getPatientId()),
            LocalDateTime.parse(consultationDTO.getConsultationDTOStartDateTime()),
            LocalDateTime.parse(consultationDTO.getConsultationDTOEndDateTime()),
            ConsultationStatus.valueOf(consultationDTO.getConsultationDTOStatus()),
            consultationDTO.getConsultationDTONotePath(),
            new BigDecimal(consultationDTO.getConsultationDTOAmount()),
            Boolean.valueOf(consultationDTO.getConsultationDTOAmountPaid())
        );
    }
    
    /**
    * Checks if a time slot is already booked
    * @param startDateTime Start time of the proposed consultation
    * @param endDateTime End time of the proposed consultation
    * @return true if the time slot is already booked, false if available
    * @throws BusinessException If there's an error checking availability
    */
   public boolean isTimeSlotBooked(LocalDateTime startDateTime, LocalDateTime endDateTime) throws BusinessException {
       // Validate that end time is after start time
       if (endDateTime.isBefore(startDateTime)) {
           throw new BusinessException("La hora de fin debe ser posterior a la hora de inicio");
       }

       try {
           return consultationDAO.isTimeSlotBooked(startDateTime, endDateTime);
       } catch (DataAccessException e) {
           throw new BusinessException("Error al verificar disponibilidad en la base de datos", e);
       }
    }
}