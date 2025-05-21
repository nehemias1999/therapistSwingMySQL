package com.application.services;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.ConstraintViolationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.exceptions.runtimeExceptions.dataAccessException.EntityNotFoundException;
import com.application.model.dao.PatientDAO;
import com.application.model.dto.PatientDTO;
import com.application.model.entities.Patient;
import com.application.utils.PatientsFilesManager;
import java.io.IOException;
import java.nio.file.Path;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PatientService {
    private final PatientDAO patientDAO;
    private final PatientsFilesManager fileManager;
    
    // Patron simple para validar e-mail (puede ajustarse si se requiere más estricto)
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    // Patron simple para teléfono (solo dígitos, opcional “+” al inicio, 7–15 dígitos)
    private static final Pattern PHONE_PATTERN =
        Pattern.compile("^\\+?\\d{7,15}$");
    
    public PatientService() {
        this.patientDAO = new PatientDAO();
        this.fileManager = new PatientsFilesManager(); 
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
            throw new BusinessException("Error al listar pacientes", e);
        }
    }

    /**
     * Inserta un nuevo paciente
     * @param patientDTO Datos del paciente a insertar
     * @throws ValidationException Si los datos no son válidos o el paciente ya existe
     * @throws BusinessException Si ocurre otro error de negocio
     * @throws java.io.IOException
     */
    public void insertPatient(PatientDTO patientDTO) throws ValidationException, BusinessException, IOException {
        try {
            validatePatientData(patientDTO, false);
            Patient patient = createPatientFromDTO(patientDTO, false);
            patientDAO.insertPatient(patient);  
            fileManager.initPatientFolders(patient.getPatientId());
            if(!patientDTO.getPatientDTOPhotoPath().isEmpty()) {
                fileManager.movePhotoToPatientDir(patient.getPatientId(), Path.of(patientDTO.getPatientDTOPhotoPath()));
            }
        } catch (ConstraintViolationException e) {
            throw new ValidationException("Ya existe un paciente con ese " +
                    ("DNI".equalsIgnoreCase(e.getField()) ? "DNI" : "e-mail"));
        } catch (DataAccessException e) {
            throw new BusinessException("Error de base de datos al insertar paciente", e);
        }
    }
    
    public void updatePatient(PatientDTO patientDTO) throws ValidationException, BusinessException, IOException {
        try {
            validatePatientData(patientDTO, true);
            Patient patient = createPatientFromDTO(patientDTO, true);
            patientDAO.updatePatient(patient);
            if(fileManager.hasPatientPhoto(patient.getPatientId())) {
                if(!patientDTO.getPatientDTOPhotoPath().isEmpty()) {
                    fileManager.movePhotoToPatientDir(patient.getPatientId(), Path.of(patientDTO.getPatientDTOPhotoPath()));
                } else {
                    fileManager.deletePatientPhoto(patient.getPatientId());
                }
            }
            if(!fileManager.hasPatientPhoto(patient.getPatientId()) && !patientDTO.getPatientDTOPhotoPath().isEmpty()) {
                fileManager.movePhotoToPatientDir(patient.getPatientId(), Path.of(patientDTO.getPatientDTOPhotoPath()));
            }
        } catch (EntityNotFoundException e) {
            throw new ValidationException("No existe paciente con Id '" + patientDTO.getPatientDTOId() + "'");
        } catch (ConstraintViolationException e) {
            throw new ValidationException("Ya existe otro paciente con ese " +
                    ("DNI".equalsIgnoreCase(e.getField()) ? "DNI" : "e-mail"));
        } catch (DataAccessException e) {
            throw new BusinessException("Error de base de datos al actualizar paciente", e);
        }
    }
    
    public void deletePatient(String id) throws ValidationException, BusinessException {
        try {
            patientDAO.deletePatient(UUID.fromString(id));
        } catch (EntityNotFoundException e) {
            throw new ValidationException("No existe paciente con Id '" + id + "'");
        } catch (DataAccessException e) {
            throw new BusinessException("Error de base de datos al eliminar paciente", e);
        }
    }

    public PatientDTO getPatientById(String patientId) throws ValidationException, BusinessException {
        try {
            return convertToDTO(patientDAO.getPatientById(UUID.fromString(patientId)));
        } catch (EntityNotFoundException e) {
            throw new ValidationException("No existe paciente con Id '" + patientId + "'");
        } catch (DataAccessException e) {
            throw new BusinessException("Error de base de datos al encontrar paciente", e);
        }
    }
    
    /**
     * Valida los datos del paciente antes de la inserción
     */
    private void validatePatientData(PatientDTO dto, Boolean isUpdate) throws ValidationException, DataAccessException {
        // DNI: obligatorio, 8 dígitos
        String dni = dto.getPatientDTODNI();
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new ValidationException("El DNI debe contener exactamente 8 dígitos");
        }
        // Nombre
        if (dto.getPatientDTOName() == null || dto.getPatientDTOName().trim().isEmpty()) {
            throw new ValidationException("El nombre del paciente es requerido");
        }
        
        // Apellido
        if (dto.getPatientDTOLastName() == null || dto.getPatientDTOLastName().trim().isEmpty()) {
            throw new ValidationException("El apellido del paciente es requerido");
        }
        // Fecha de nacimiento
        String birth = dto.getPatientDTOBirthDate();
        if (birth == null || birth.trim().isEmpty()) {
            throw new ValidationException("La fecha de nacimiento es requerida");
        }
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(birth);
        } catch (Exception ex) {
            throw new ValidationException("Formato de fecha inválido (esperado YYYY-MM-DD)");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new ValidationException("La fecha de nacimiento no puede ser futura");
        }
        
        // Ocupacion
        if (dto.getPatienDTOOccupation() == null || dto.getPatienDTOOccupation().trim().isEmpty()) {
            throw new ValidationException("La ocupacion del paciente es requerida");
        }
        
        // Teléfono
        String phone = dto.getPatientDTOPhone();
        if (phone == null || !PHONE_PATTERN.matcher(phone.trim()).matches()) {
            throw new ValidationException("Teléfono inválido (solo dígitos, 7–15 caracteres)");
        }
        
        // E-mail
        String email = dto.getPatientDTOEmail();
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("E-mail inválido");
        }
        
        // Ciudad
        String cityId = dto.getCityId();
        if (cityId == null || cityId.trim().isEmpty()) {
            throw new ValidationException("La ciudad es requerida");
        }
        
        // Dirección
        if (dto.getPatientDTOAddress() == null || dto.getPatientDTOAddress().trim().isEmpty()) {
            throw new ValidationException("La dirección es requerida");
        }
        
        // Número
        String num = dto.getPatientDTOAddressNumber();
        if (num == null || !num.matches("\\d+")) {
            throw new ValidationException("El número de dirección debe ser numérico");
        }
        
        // Piso (opcional)
        String floor = dto.getPatientDTOAddressFloor();
        if (floor != null && !floor.trim().isEmpty() && !floor.matches("\\d+")) {
            throw new ValidationException("El piso debe ser un número entero");
        }
        
        // Departamento (opcional)
        
        // Unicidad: DNI y e-mail
        // En creación o si cambiaron en edición
        if (!isUpdate || patientDAO.isPatientDNIExists(dni.trim()) 
            && (isUpdate && !dto.getPatientDTOId().isEmpty()
                 && !dni.trim().equalsIgnoreCase(
                     patientDAO.getPatientByDNI(dni.trim()).getPatientDNI()))) {
            if (patientDAO.isPatientDNIExists(dni.trim())) {
                throw new ValidationException("Ya existe un paciente con DNI '" + dni.trim() + "'");
            }
        }
        if (!isUpdate || patientDAO.isPatientEmailExists(email.trim())
            && (isUpdate && !dto.getPatientDTOId().isEmpty()
                 && !email.trim().equalsIgnoreCase(
                     patientDAO.getPatientByDNI(dni.trim()).getPatientEmail()))) {
            if (patientDAO.isPatientEmailExists(email.trim())) {
                throw new ValidationException("Ya existe un paciente con e-mail '" + email.trim() + "'");
            }
        }
    }

    /**
     * Crea un objeto Patient a partir de un PatientDTO
     */
    public Patient createPatientFromDTO(PatientDTO dto, Boolean isUpdate) {
        Patient p = new Patient();
        if(!isUpdate) {
            p.setPatientId(UUID.randomUUID());
        } else {
            p.setPatientId(UUID.fromString(dto.getPatientDTOId().trim()));
        }

        p.setPatientDNI(dto.getPatientDTODNI().trim());
        p.setPatientName(dto.getPatientDTOName().toLowerCase().trim());
        p.setPatientLastName(dto.getPatientDTOLastName().toLowerCase().trim());
        p.setPatientBirthDate(LocalDate.parse(dto.getPatientDTOBirthDate().trim()));
        p.setPatientOccupation(dto.getPatienDTOOccupation().toLowerCase().trim());
        p.setPatientPhone(dto.getPatientDTOPhone().toLowerCase().trim());
        p.setPatientEmail(dto.getPatientDTOEmail().toLowerCase().trim());
        p.setCityId(UUID.fromString(dto.getCityId().trim()));
        p.setPatientAddress(dto.getPatientDTOAddress().toLowerCase().trim());
        p.setPatientAddressNumber(Integer.parseInt(dto.getPatientDTOAddressNumber().trim()));
        p.setPatientAddressFloor(
            (dto.getPatientDTOAddressFloor() != null && !dto.getPatientDTOAddressFloor().isEmpty())
            ? Integer.parseInt(dto.getPatientDTOAddressFloor().trim())
            : 0
        );
        p.setPatientAddressDepartment(
            dto.getPatientDTOAddressDepartment() != null 
            ? dto.getPatientDTOAddressDepartment().toLowerCase().trim() 
            : null
        );
        return p;
    }

    private PatientDTO convertToDTO(Patient p) {
        PatientDTO dto = new PatientDTO();
        dto.setPatientDTOId(p.getPatientId().toString());
        dto.setPatientDTODNI(p.getPatientDNI());
        dto.setPatientDTOName(p.getPatientName());
        dto.setPatientDTOLastName(p.getPatientLastName());
        dto.setPatientDTOBirthDate(p.getPatientBirthDate().toString());
        dto.setPatientDTOOccupation(p.getPatientOccupation());
        dto.setPatientDTOPhone(p.getPatientPhone());
        dto.setPatientDTOEmail(p.getPatientEmail());
        dto.setCityId(p.getCityId().toString());
        dto.setPatientDTOAddress(p.getPatientAddress());
        dto.setPatientDTOAddressNumber(String.valueOf(p.getPatientAddressNumber()));
        dto.setPatientDTOAddressFloor(
            p.getPatientAddressFloor() > 0 ? String.valueOf(p.getPatientAddressFloor()) : ""
        );
        dto.setPatientDTOAddressDepartment(
            p.getPatientAddressDepartment() != null ? p.getPatientAddressDepartment() : ""
        );
        // photoPath
        try {
            if (fileManager.hasPatientPhoto(p.getPatientId())) {
                dto.setPatientDTOPhotoPath(
                    fileManager.getPatientPhoto(p.getPatientId())
                               .toString()
                );
            } else {
                dto.setPatientDTOPhotoPath("");
            }
        } catch (IOException ioe) {
            // En caso de fallo de E/S, devolvemos vacío (o podríamos loggear)
            dto.setPatientDTOPhotoPath("");
        }
        return dto;
    }
}