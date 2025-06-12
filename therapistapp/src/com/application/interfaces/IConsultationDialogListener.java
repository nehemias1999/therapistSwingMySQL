package com.application.interfaces;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.ConsultationDTO;
import com.application.model.dto.PatientDTO;
import java.io.IOException;
import java.util.List;

public interface IConsultationDialogListener {
    
    ConsultationDTO getConsultationById(String consultationId); 
    
    List<PatientDTO> getPatientsByConsultationId(String consultationId);
    
    void insertConsultation(ConsultationDTO consultationDTO, List<String> consultationPatientsId) throws ValidationException, BusinessException, IOException;
    
    void updateConsultation(ConsultationDTO consultationDTO, List<String> consultationPatientsId) throws ValidationException, BusinessException, IOException;
    
    PatientDTO getPatientById(String patientId);
    
    Boolean isConsultationPatientPaid(String consultationId, String patientId) throws ValidationException, BusinessException;
    
    void setConsultationPatientPaid(String consultationId, String patientId) throws ValidationException, BusinessException;
        
}

