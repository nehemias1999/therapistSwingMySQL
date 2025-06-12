package com.application.controllers.panels;

import com.application.controllers.entities.ControllerRegistry;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.CityDTO;
import com.application.model.dto.ConsultationDTO;
import com.application.model.dto.PatientDTO;
import com.application.view.panels.consultation.ConsultationsPanel;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ConsultationsPanelController {
    
    private final ControllerRegistry controllerRegistry;
    private final ConsultationsPanel consultationsForm;
    
    public ConsultationsPanelController(ControllerRegistry controllerRegistry) {
        this.controllerRegistry = controllerRegistry;
        
        this.consultationsForm = new ConsultationsPanel();
        this.consultationsForm.setController(this);
    }
    
    public ConsultationsPanel getView() {
        return consultationsForm;
    }
          
    /**
     * Inserta una nueva consulta en el sistema
     * @param consultationDTO Datos de la consulta a insertar
     * @param consultationPatientsId Identificadores de los pacientes
     * @throws ValidationException Si los datos no son válidos o la consulta ya existe
     * @throws BusinessException Si ocurre un error durante el proceso
     * @throws java.io.IOException
     */
    public void insertConsultation(ConsultationDTO consultationDTO, List<String> consultationPatientsId) throws ValidationException, BusinessException, IOException {
        controllerRegistry.getConsultationController()
                .insertConsultation(consultationDTO);
        controllerRegistry.getConsultationPatientController()
                .insertConsultationPatients(consultationDTO.getConsultationDTOId(), consultationPatientsId);
    }
    
    /**
     * Modifica una consulta existente en el sistema
     * @param consultationDTO Datos de la consulta a modificar
     * @param consultationPatientsId Identificadores delos pacientes
     * @throws ValidationException Si los datos no son válidos o la consulta ya existe
     * @throws BusinessException Si ocurre un error durante el proceso
     * @throws java.io.IOException
     */
    public void updateConsultation(ConsultationDTO consultationDTO, List<String> consultationPatientsId) throws ValidationException, BusinessException, IOException {
        controllerRegistry.getConsultationController()
                .updateConsultation(consultationDTO);
        controllerRegistry.getConsultationPatientController()
                .updateConsultationPatients(consultationDTO.getConsultationDTOId(), consultationPatientsId);
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
        return controllerRegistry.getConsultationPatientController()
                .isConsultationPatientPaid(consultationId, patientId);
    }
    
    /**
     * Modifica al paciente para que se setee la consulta en paga
     * @param consultationId Identificador de la consulta
     * @param patientId Identificador del paciente
     * @throws ValidationException Si los datos no son válidos o la consulta ya existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void setConsultationPatientPaid(String consultationId, String patientId) throws ValidationException, BusinessException {
        controllerRegistry.getConsultationPatientController()
                .setConsultationPatientPaid(consultationId, patientId);
    }
    
    /**
     * Elimina una consulta existente en el sistema
     * @param consultationId de la consulta a eliminar
     */
    public void deleteConsultation(String consultationId) {
        try {
            controllerRegistry.getConsultationController().deleteConsultation(consultationId);
        } catch (ValidationException e) {
            consultationsForm.showErrorMessage("Validación: " + e.getMessage());
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage("Error: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene una consulta para un Identificador determinado
     * @param consultationId Identificador de la consulta a buscar
     * @return DTO de la consulta
     */
    public ConsultationDTO getConsultationById(String consultationId)  {
        try {
            return controllerRegistry.getConsultationController().getConsultationById(consultationId);
        } catch (ValidationException e) {
            consultationsForm.showErrorMessage("Validación: " + e.getMessage());
            return null;
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage("Error al obtener consulta: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Obtiene las consulta para un dia determinado
     * @param consultationDate fecha de las consultas a buscar
     * @return lista de DTOs de consulta para la fecha especificada
     */
    public List<ConsultationDTO> getConsultationsByDate(String consultationDate) {
        try {
            return controllerRegistry.getConsultationController().getConsultationsByDate(consultationDate);
        } catch (ValidationException e) {
            consultationsForm.showErrorMessage("Validación: " + e.getMessage());
            return List.of();
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage("Error al obtener consultas por fecha: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Obtiene todos los pacientes 
     * @return lista de DTO's de los pacientes
     */
    public List<PatientDTO> getAllPatients() {
        try {
            return controllerRegistry.getPatientController().getAllPatients();
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage("Error al obtener pacientes: " + e.getMessage());
            return List.of();
        }
    }
        
    /**
     * Obtiene los nombres de los pacientes asociados a una consulta determinada
     * @param consultationId Identificador de la consulta
     * @return lista de DTO's de los pacientes asociados a la consulta
     */
    public List<PatientDTO> getPatientsByConsultationId(String consultationId) {
        try {
            return controllerRegistry.getConsultationPatientController().getPatientsByConsultationId(consultationId);
        } catch (ValidationException e) {
            consultationsForm.showErrorMessage("Validación: " + e.getMessage());
            return List.of();
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage("Error al obtener pacientes de la consulta: " + e.getMessage());
            return List.of();
        }
    }
          
    /**
     * Obtiene el paciente en base a su Id
     * @param patientId
     * @return PatientDTO
     */
    public PatientDTO getPatientById(String patientId) {
        try {
            return controllerRegistry.getPatientController().getPatientById(patientId);
        } catch (ValidationException | BusinessException e) {
            consultationsForm.showErrorMessage(e.getMessage());
            return null;
        }
    } 
    
    /**
     * Obtiene todas las ciudades en el sistema
     * @return List CityDTO
     */
    public List<CityDTO> getAllCities() {
        try {
            List<CityDTO> cities = controllerRegistry.getCityController().getAllCities();
            return cities != null ? cities : Collections.emptyList();
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage("Error al obtener ciudades: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Inserta un nuevo paciente
     * @param patientDTO Datos del paciente a insertar
     * @throws com.application.exceptions.businessException.ValidationException
     * @throws com.application.exceptions.businessException.BusinessException
     * @throws java.io.IOException
     */
    public void insertPatient(PatientDTO patientDTO) throws ValidationException, BusinessException, IOException {
        controllerRegistry.getPatientController().insertPatient(patientDTO);
    }
    
    /**
     * Modifica paciente existente
     * @param patientDTO Datos del paciente a modificar
     * @throws com.application.exceptions.businessException.ValidationException
     * @throws com.application.exceptions.businessException.BusinessException
     * @throws java.io.IOException
     */
    public void updatePatient(PatientDTO patientDTO) throws ValidationException, BusinessException, IOException {
        controllerRegistry.getPatientController().updatePatient(patientDTO);
    }
}