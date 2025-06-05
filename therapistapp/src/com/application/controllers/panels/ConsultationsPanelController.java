package com.application.controllers.panels;

import com.application.controllers.entities.ConsultationController;
import com.application.controllers.entities.ConsultationPatientController;
import com.application.controllers.entities.PatientController;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.ConsultationDTO;
import com.application.model.dto.PatientDTO;
import com.application.view.panels.consultation.ConsultationsPanel;
import java.util.List;

public class ConsultationsPanelController {
    
    private final ConsultationController consultationController;
    private final ConsultationPatientController consultationPatientController;
    private final PatientController patientController;
    private final ConsultationsPanel consultationsForm;
    
    public ConsultationsPanelController(
            ConsultationController consultationController, 
            ConsultationPatientController consultationPatientController,
            PatientController patientController) {
        this.consultationController = consultationController;
        this.consultationPatientController = consultationPatientController;
        this.patientController = patientController;
        
        this.consultationsForm = new ConsultationsPanel();
        this.consultationsForm.setController(this);
    }
    
    public ConsultationsPanel getView() {
        return consultationsForm;
    }
          
    /**
     * Inserta una nueva consulta en el sistema
     * @param consultationDTO Datos de la consulta a insertar
     */
    public void insertConsultation(ConsultationDTO consultationDTO) {
        try {
            consultationController.insertConsultation(consultationDTO);
            consultationsForm.showInformationMessage("Consulta agregada exitosamente.");
        } catch (ValidationException e) {
            consultationsForm.showErrorMessage("Validación: " + e.getMessage());
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage("Error: " + e.getMessage());
        }
    }
    
    /**
     * Modifica una consulta existente en el sistema
     * @param consultationDTO Datos de la consulta a modificar
     */
    public void updateConsultation(ConsultationDTO consultationDTO) {
        try {
            consultationController.updateConsultation(consultationDTO);
            consultationsForm.showInformationMessage("Consulta modificada exitosamente.");
        } catch (ValidationException e) {
            consultationsForm.showErrorMessage("Validación: " + e.getMessage());
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage("Error: " + e.getMessage());
        }
    }
    
    /**
     * Elimina una consulta existente en el sistema
     * @param consultationId de la consulta a eliminar
     */
    public void deleteConsultation(String consultationId) {
        try {
            consultationController.deleteConsultation(consultationId);
            consultationsForm.showInformationMessage("Consulta eliminada exitosamente.");
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
            return consultationController.getConsultationById(consultationId);
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
            return consultationController.getConsultationsByDate(consultationDate);
        } catch (ValidationException e) {
            consultationsForm.showErrorMessage("Validación: " + e.getMessage());
            return List.of();
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage("Error al obtener consultas por fecha: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Obtiene el monto de una consulta determinada
     * @param consultationId Identificador de la consulta a buscar pacientes
     * @return Monto de la consulta 
     */
    public String getConsultationAmountByConsultationId(String consultationId) {
        try {
            return consultationController.getConsultationAmountByConsultationId(consultationId);
        } catch (ValidationException e) {
            consultationsForm.showErrorMessage("Validación: " + e.getMessage());
            return null;
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage("Error al obtener monto de la consulta: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Obtiene todos los pacientes 
     * @return lista de DTO's de los pacientes
     */
    public List<PatientDTO> getAllPatients() {
        try {
            return patientController.getAllPatients();
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
            return consultationPatientController.getPatientsByConsultationId(consultationId);
        } catch (ValidationException e) {
            consultationsForm.showErrorMessage("Validación: " + e.getMessage());
            return List.of();
        } catch (BusinessException e) {
            consultationsForm.showErrorMessage("Error al obtener pacientes de la consulta: " + e.getMessage());
            return List.of();
        }
    }
}
