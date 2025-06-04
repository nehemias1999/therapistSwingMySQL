package com.application.interfaces;

import com.application.model.dto.ConsultationDTO;
import com.application.model.dto.PatientDTO;
import java.util.List;

public interface IConsultationDialogListener {
    ConsultationDTO getConsultationById(String consultationId); 
    String getConsultationAmountByConsultationId(String consultationId);
    List<PatientDTO> getPatientsByConsultationId(String consultationId);
}

