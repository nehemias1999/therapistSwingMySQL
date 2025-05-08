package com.application.controllers.entities;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.ConsultationDTO;
import com.application.services.ConsultationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ConsultationController {
    private final ConsultationService consultationService;
    
    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    /**
     * Schedules a new consultation
     * @param consultationDTO Consultation data to schedule
     * @throws ValidationException If input data is invalid
     * @throws BusinessException If there's a business error
     */
    public void scheduleConsultation(ConsultationDTO consultationDTO) throws ValidationException, BusinessException {
        consultationService.insertConsultation(consultationDTO);
    }

    /**
     * Validates consultation data before scheduling
     * @param consultationDTO Consultation data to validate
     * @throws ValidationException If validation fails
     */
    public void validateConsultationData(ConsultationDTO consultationDTO) throws ValidationException {
        if (consultationDTO.getPatientId() == null || consultationDTO.getPatientId().trim().isEmpty()) {
            throw new ValidationException("El ID del paciente es requerido");
        }
        // Additional validations are handled by the service layer
    }

    /**
     * Checks if a time slot is available
     * @param startDateTime Proposed start time
     * @param endDateTime Proposed end time
     * @return true if available, false if already booked
     * @throws BusinessException If there's an error checking availability
     */
    public boolean isTimeSlotAvailable(LocalDateTime startDateTime, LocalDateTime endDateTime) throws BusinessException {
        try {
            return !consultationService.isTimeSlotBooked(startDateTime, endDateTime);
        } catch (BusinessException e) {
            throw new BusinessException("Error al verificar disponibilidad: " + e.getMessage(), e);
        }
    }

    /**
     * Creates a consultation DTO with required fields
     * @param patientId Patient ID
     * @param startDateTime Consultation start time
     * @param endDateTime Consultation end time
     * @param status Consultation status
     * @param notesPath
     * @param amount Consultation amount
     * @param paid Payment status
     * @return Prepared ConsultationDTO
     */
    public ConsultationDTO createConsultationDTO(
            String patientId,
            String startDateTime,
            String endDateTime,
            String status,
            String notesPath,
            String amount,
            boolean paid) {
        
        ConsultationDTO dto = new ConsultationDTO(
            patientId,
            startDateTime,
            endDateTime,
            status,
            notesPath,
            amount,
            String.valueOf(paid)
        );
        
        return dto;
    }
}