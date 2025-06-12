package com.application.services;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import com.application.exceptions.runtimeExceptions.dataAccessException.EntityNotFoundException;

import java.util.UUID;

import com.application.model.dao.ConsultationPatientDAO;
import com.application.model.dto.ConsultationPatientDTO;
import com.application.model.dto.PatientDTO;
import com.application.model.entities.ConsultationPatient;
import com.application.model.entities.Patient;
import com.application.utils.PatientsFilesManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultationPatientService {
    private final ConsultationPatientDAO consultationPatientDAO;
    private final PatientsFilesManager patientFileManager;

    public ConsultationPatientService() {
        this.consultationPatientDAO = new ConsultationPatientDAO();
        this.patientFileManager = new PatientsFilesManager();
    }
    
    /**
     * Inserta una lista de pacientes en una consulta existente en el sistema
     * @param consultationId Identificador de la consulta
     * @param consultationPatientsId Identificadores de los pacientes
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     * @throws java.io.IOException
     */
    public void insertConsultationPatients(String consultationId, List<String> consultationPatientsId) throws ValidationException, BusinessException, IOException {
        try {
            List<ConsultationPatient> consultationPatients = createConsultationPatients(consultationId, consultationPatientsId);
            for(ConsultationPatient cp: consultationPatients) {
                consultationPatientDAO.insertConsultationPatient(cp);
            }
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Id o datos mal formateados", e);
        } catch (DataAccessException e) {
            throw new BusinessException("Error al guardar la consulta en el sistema", e);
        }
    }
    
    private List<ConsultationPatient> createConsultationPatients(String consultationId, List<String> consultationPatientsId) throws IOException {
        List<ConsultationPatient> result = new ArrayList<>();
        Boolean isGroupSession = consultationPatientsId.size() > 1;
        
        for(String patientId: consultationPatientsId) {
            UUID consultationUUID = UUID.fromString(consultationId);
            UUID patientUUID = UUID.fromString(patientId);
            
            ConsultationPatient cp = new ConsultationPatient(
                    consultationUUID,
                    patientUUID,
                    Boolean.FALSE,
                    patientFileManager.createWordNoteFile(consultationUUID, patientUUID, isGroupSession).toString()
            );
            result.add(cp);
        }
        return result;
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
            UUID cId = UUID.fromString(consultationId);
            UUID pId = UUID.fromString(patientId);
            consultationPatientDAO.deleteConsultationPatient(cId, pId);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("ID de consulta o paciente mal formado", e);
        } catch (EntityNotFoundException e) {
            throw new ValidationException("Consulta o paciente no encontrado", e);
        } catch (DataAccessException e) {
            throw new BusinessException("Error al eliminar la consulta del sistema", e);
        }
    }
      
    /**
     * Obtiene los pacientes de una consulta determinada
     * @param consultationId
     * @return Lista de PatientDTO
     * @throws BusinessException Si ocurre un error al acceder a los datos
     */
    public List<PatientDTO> getPatientsByConsultationId(String consultationId) throws BusinessException {
        try {
            UUID cId = UUID.fromString(consultationId);
            return consultationPatientDAO.getPatientsByConsultationId(cId)
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("ID de consulta mal formado", e);
        } catch (DataAccessException e) {
            throw new BusinessException("Error al listar pacientes", e);
        }
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
        try {
            UUID cId = UUID.fromString(consultationId);
            UUID pId = UUID.fromString(patientId);
            return consultationPatientDAO.isConsultationPatientPaid(cId, pId);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("ID mal formado", e);
        } catch (EntityNotFoundException e) {
            throw new ValidationException("Consulta o paciente no encontrado", e);
        } catch (DataAccessException e) {
            throw new BusinessException("Error de base de datos al actualizar estado de pago", e);
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
            UUID cId = UUID.fromString(consultationId);
            UUID pId = UUID.fromString(patientId);
            consultationPatientDAO.setConsultationPatientPaid(cId, pId);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("ID mal formado", e);
        } catch (EntityNotFoundException e) {
            throw new ValidationException("Consulta o paciente no encontrado", e);
        } catch (DataAccessException e) {
            throw new BusinessException("Error de base de datos al actualizar estado de pago", e);
        }
    }
   
    /**
     * Valida los datos básicos de la consulta
     */
    private void validateConsultationPatientData(ConsultationPatientDTO dto) throws ValidationException {
        if (dto.getIsPaid() == null || dto.getIsPaid().trim().isEmpty()) {
            throw new ValidationException("El estado del pago es requerido");
        }
        if (dto.getPatientNotePath() == null || dto.getPatientNotePath().trim().isEmpty()) {
            throw new ValidationException("El path de las notas del paciente es requerido");
        }
    }
    
    /**
     * Crea un objeto ConsultationPatient a partir de un ConsultationPatientDTO
     */
    private ConsultationPatient createConsultationPatientFromDTO(ConsultationPatientDTO dto) {
        return new ConsultationPatient(
            UUID.fromString(dto.getConsultationId()),
            UUID.fromString(dto.getPatientId()),
            Boolean.valueOf(dto.getIsPaid()),
            dto.getPatientNotePath()
        );
    }
    
    /**
     * Crea un objeto ConsultationPatientDTO a partir de un ConsultationPatient
     */
    private ConsultationPatientDTO convertToDTO(ConsultationPatient cp) {
        ConsultationPatientDTO dto = new ConsultationPatientDTO();
        dto.setConsultationId(cp.getConsultationId().toString());
        dto.setPatientId(cp.getPatientId().toString());
        dto.setIsPaid(cp.getIsPaid().toString());
        dto.setPatientNotePath(cp.getPatientNotePath());
        
        return dto;
    }
    
    /**
     * Crea un objeto PatientDTO a partir de un Patient
     */
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
        dto.setPatientDTOAddressFloor(p.getPatientAddressFloor() > 0 ? String.valueOf(p.getPatientAddressFloor()) : "");
        dto.setPatientDTOAddressDepartment(p.getPatientAddressDepartment() != null ? p.getPatientAddressDepartment() : "");

        try {
            if (patientFileManager.hasPatientPhoto(p.getPatientId())) {
                dto.setPatientDTOPhotoPath(patientFileManager.getPatientPhoto(p.getPatientId()).toString());
            } else {
                dto.setPatientDTOPhotoPath("");
            }
        } catch (IOException e) {
            dto.setPatientDTOPhotoPath("");
        }

        return dto;
    }
}