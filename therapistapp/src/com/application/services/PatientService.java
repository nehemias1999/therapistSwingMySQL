package com.application.services;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.ConstraintViolationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.model.dao.PatientDAO;
import com.application.model.dto.PatientDTO;
import com.application.model.entities.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PatientService {
    private final PatientDAO patientDAO;
    
    public PatientService() {
        this.patientDAO = new PatientDAO();
    }
    
    /**
     * Obtiene todos los pacientes convertidos a DTO
     * @return Lista de PatientDTO
     * @throws BusinessException Si ocurre un error al acceder a los datos
     */
    public List<PatientDTO> getAllPatients() throws BusinessException {
        try {
            return patientDAO.getAllPatients().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new BusinessException("Error al obtener el listado de pacientes", e);
        }
    }

    /**
     * Inserta un nuevo paciente
     * @param patientDTO Datos del paciente a insertar
     * @throws ValidationException Si los datos no son válidos o el paciente ya existe
     * @throws BusinessException Si ocurre otro error de negocio
     */
    public void insertPatient(PatientDTO patientDTO) throws ValidationException, BusinessException {
        // Validaciones básicas de datos de entrada
        validatePatientData(patientDTO);

        try {
            // Verificar si el paciente ya existe por DNI o email
            if (patientDAO.isPatientDNIExists(patientDTO.getPatientDTODNI())) {
                throw new ValidationException("Ya existe un paciente con DNI: " + patientDTO.getPatientDTODNI());
            }

            if (patientDAO.isPatientEmailExists(patientDTO.getPatientDTOEmail())) {
                throw new ValidationException("Ya existe un paciente con email: " + patientDTO.getPatientDTOEmail());
            }

            // Crear y guardar el nuevo paciente
            Patient patient = createPatientFromDTO(patientDTO);
            patientDAO.insertPatient(patient);

        } catch (ConstraintViolationException e) {
            // Esto podría ocurrir si hay una condición de carrera
            throw new ValidationException("El paciente ya existe en el sistema");
        } catch (DataAccessException e) {
            throw new BusinessException("Error al guardar el paciente en el sistema", e);
        }
    }

    /**
     * Valida los datos del paciente antes de la inserción
     */
    private void validatePatientData(PatientDTO patientDTO) throws ValidationException {
        if (patientDTO.getPatientDTODNI() == null || patientDTO.getPatientDTODNI().trim().isEmpty()) {
            throw new ValidationException("El DNI del paciente es requerido");
        }

        if (patientDTO.getPatientDTOName() == null || patientDTO.getPatientDTOName().trim().isEmpty()) {
            throw new ValidationException("El nombre del paciente es requerido");
        }

        if (patientDTO.getPatientDTOLastName() == null || patientDTO.getPatientDTOLastName().trim().isEmpty()) {
            throw new ValidationException("El apellido del paciente es requerido");
        }

        if (patientDTO.getPatientDTOBirthDate() == null || patientDTO.getPatientDTOBirthDate().trim().isEmpty()) {
            throw new ValidationException("La fecha de nacimiento es requerida");
        }

        LocalDate birthDate = LocalDate.parse(patientDTO.getPatientDTOBirthDate());
        if (birthDate.isAfter(LocalDate.now())) {
            throw new ValidationException("La fecha de nacimiento no puede ser posterior al día actual");
        }

        if (patientDTO.getPatientDTOPhone() == null || patientDTO.getPatientDTOPhone().trim().isEmpty()) {
            throw new ValidationException("El teléfono del paciente es requerido");
        }

        if (patientDTO.getPatientDTOEmail() == null || patientDTO.getPatientDTOEmail().trim().isEmpty()) {
            throw new ValidationException("El email del paciente es requerido");
        }

        if (patientDTO.getCityId() == null || patientDTO.getCityId().trim().isEmpty()) {
            throw new ValidationException("La ciudad es requerida");
        }

        if (patientDTO.getPatientDTOAddress() == null || patientDTO.getPatientDTOAddress().trim().isEmpty()) {
            throw new ValidationException("La dirección es requerida");
        }

        if (patientDTO.getPatientDTOAddressNumber() == null || patientDTO.getPatientDTOAddressNumber().trim().isEmpty()) {
            throw new ValidationException("El número de dirección es requerido");
        }
    }

    /**
     * Crea un objeto Patient a partir de un PatientDTO
     */
    private Patient createPatientFromDTO(PatientDTO patientDTO) {
        return new Patient(
            UUID.randomUUID(),
            patientDTO.getPatientDTODNI().trim().toLowerCase(),
            patientDTO.getPatientDTOName().trim().toLowerCase(),
            patientDTO.getPatientDTOLastName().trim().toLowerCase(),
            LocalDate.parse(patientDTO.getPatientDTOBirthDate()),
            patientDTO.getPatientDTOPhone().trim().toLowerCase(),
            patientDTO.getPatientDTOEmail().trim().toLowerCase(),
            UUID.fromString(patientDTO.getCityId()),
            patientDTO.getPatientDTOAddress().trim().toLowerCase(),
            Integer.parseInt(patientDTO.getPatientDTOAddressNumber()),
            patientDTO.getPatientDTOAddressFloor() != null && !patientDTO.getPatientDTOAddressFloor().isEmpty() ? 
                Integer.parseInt(patientDTO.getPatientDTOAddressFloor()) : 0,
            patientDTO.getPatientDTOAddressApartment() != null ? 
                patientDTO.getPatientDTOAddressApartment().trim().toLowerCase() : null
        );
    }

    /**
     * Convierte una entidad Patient a PatientDTO
     */
    private PatientDTO convertToDTO(Patient patient) {
        return new PatientDTO(
            patient.getPatientId().toString(),
            patient.getPatientDNI(),
            patient.getPatientName(),
            patient.getPatientLastName(),
            patient.getPatientBirthDate().toString(),
            patient.getPatientPhone(),
            patient.getPatientEmail(),
            patient.getCityId().toString(),
            patient.getPatientAddress(),
            String.valueOf(patient.getPatientAddressNumber()),
            patient.getPatientAddressFloor() > 0 ? String.valueOf(patient.getPatientAddressFloor()) : "",
            patient.getPatientAddressApartment() != null ? patient.getPatientAddressApartment() : ""
        );
    }
}