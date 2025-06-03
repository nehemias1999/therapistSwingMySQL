package com.application.controllers.panels;

import com.application.controllers.entities.ConsultationController;
import com.application.controllers.entities.ConsultationPatientController;
import com.application.controllers.entities.PatientController;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.ConsultationDTO;
import com.application.model.dto.ConsultationPatientDTO;
import com.application.model.dto.PatientDTO;
import com.application.view.panels.consultation.ConsultationsForm;
import java.io.IOException;
import java.util.List;

public class ConsultationsFormController {
    
    private final ConsultationController consultationController;
    private final ConsultationPatientController consultationPatientController;
    private final PatientController patientController;
    private final ConsultationsForm consultationsForm;
    
    public ConsultationsFormController(
            ConsultationController consultationController, 
            ConsultationPatientController consultationPatientController,
            PatientController patientController) {
        this.consultationController = consultationController;
        this.consultationPatientController = consultationPatientController;
        this.patientController = patientController;
        
        this.consultationsForm = new ConsultationsForm();
        this.consultationsForm.setController(this);
    }
    
    public ConsultationsForm getView() {
        return consultationsForm;
    }
          
    /**
     * Inserta una nueva consulta en el sistema
     * @param consultationDTO Datos de la consulta a insertar
     * @throws ValidationException Si los datos no son válidos o la consulta ya existe
     * @throws BusinessException Si ocurre un error durante el proceso
     * @throws java.io.IOException
     */
    public void insertConsultation(ConsultationDTO consultationDTO) throws ValidationException, BusinessException, IOException {
        consultationController.insertConsultation(consultationDTO);
    }
    
    /**
     * Modifica una consulta existente en el sistema
     * @param consultationDTO Datos de la consulta a modificar
     * @throws ValidationException Si los datos no son válidos o la consulta ya existe
     * @throws BusinessException Si ocurre un error durante el proceso
     * @throws java.io.IOException
     */
    public void updateConsultation(ConsultationDTO consultationDTO) throws ValidationException, BusinessException, IOException {
        consultationController.updateConsultation(consultationDTO);
    }
    
    /**
     * Elimina una consulta existente en el sistema
     * @param consultationId de la consulta a eliminar
     * @throws ValidationException Si los datos no son válidos o la consulta no existe
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public void deleteConsultation(String consultationId) throws ValidationException, BusinessException {
        consultationController.deleteConsultation(consultationId);
    }
    
    /**
     * Obtiene una consulta para un Identificador determinado
     * @param consultationId Identificador de la consulta a buscar
     * @return DTO de la consulta
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public ConsultationDTO getConsultationById(String consultationId) {
        try {
            return consultationController.getConsultationById(consultationId);
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage(e.getMessage());
            return null;
        }
    }
    
    /**
     * Obtiene las consulta para un dia determinado
     * @param consultationDate fecha de las consultas a buscar
     * @return lista de DTOs de consulta para la fecha especificada
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public List<ConsultationDTO> getConsultationsByDate(String consultationDate) {
        try {
            return consultationController.getConsultationsByDate(consultationDate);
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage(e.getMessage());
            return null;
        }
    }
    
    /**
     * Obtiene todos los pacientes 
     * @return lista de DTO's de los pacientes
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public List<PatientDTO> getAllPatients() {
        try {
            return patientController.getAllPatients();
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage(e.getMessage());
            return null;
        }
    }
    
    /**
     * Obtiene los nombres de los pacientes asociados a una consulta determinada
     * @param consultationId Identificador de la consulta
     * @return lista de DTO's de los pacientes asociados a la consulta
     * @throws BusinessException Si ocurre un error durante el proceso
     */
    public List<PatientDTO> getPatientsByConsultationId(String consultationId) {
        try {
            return consultationPatientController.getPatientsByConsultationId(consultationId);
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage(e.getMessage());
            return null;
        }
    }
}
