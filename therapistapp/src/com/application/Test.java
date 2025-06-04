/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.application;

import com.application.controllers.entities.ConsultationController;
import com.application.controllers.entities.ConsultationPatientController;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dao.PatientDAO;
import com.application.model.dto.ConsultationDTO;
import com.application.model.dto.ConsultationPatientDTO;
import com.application.model.dto.PatientDTO;
import com.application.model.entities.Consultation;
import com.application.model.entities.Patient;
import com.application.services.ConsultationPatientService;
import com.application.services.ConsultationService;
import com.application.services.PatientService;
import com.application.utils.PatientsFilesManager;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author nsalazar
 */
public class Test {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, ValidationException, BusinessException {
//        Patient patient = new Patient(
//                UUID.fromString("d6dea4d2-71be-4549-a6ad-f4b6a72be5ab"),
//                "12345690", 
//                "juan martin", 
//                "martinez", 
//                LocalDate.parse("1986-02-13"),
//                "maestro",
//                "12345678", 
//                "jmartinez@gmail.com", 
//                UUID.fromString("6172c6f4-f7b0-4f8a-96ab-2d8bacb06f08"), 
//                "moreno",
//                25,
//                0,
//                ""
//        );
//        PatientDAO patientDAO = new PatientDAO();
        
//       patientDAO.updatePatient(patient);

//        FileManager fileManager = new FileManager();
//        
//        fileManager.initPatientFolders(UUID.fromString("d6dea4d2-71be-4549-a6ad-f4b6a72be5ab"));
        
//        PatientDTO patientDTO = new PatientDTO(
//                "8efee24a-1248-4514-b51f-94796d9ebe1d",
//                "12345601", 
//                "Miguel", 
//                "alcaraz", 
//                "1995-06-18",
//                "profesor",
//                "12345680", 
//                "malcaraz@gmail.com", 
//                "6172c6f4-f7b0-4f8a-96ab-2d8bacb06f08", 
//                "mitre",
//                "350",
//                "0",
//                "",
//                ""
//        );
//        
//        PatientService ps = new PatientService();
//        
//        ps.insertPatient(patientDTO);

//        ConsultationDTO c = new ConsultationDTO(
//          "e941c0ef-2d0c-4b4b-a0e2-0a5e3cf0e278",
//          "2025-05-25T11:00:00",
//          "2025-05-25T12:00:00",
//          "SCHEDULED"
//         );
//
//        ConsultationService cs = new ConsultationService();
//        
//        ConsultationController cc = new ConsultationController(cs);
//        
//        System.out.println(cc.getConsultationAmountByConsultationId("20735758-d837-40d1-bd6e-a520e1c44866"));
        
//        cs.insertConsultation(c);
  
//        cs.updateConsultation(c);
        
//        cs.deleteConsultation("e941c0ef-2d0c-4b4b-a0e2-0a5e3cf0e278");
        
 //       List<ConsultationDTO> consultations = cs.getConsultationsByDate("2025-05-30");
        
   //     System.out.println(consultations.toString());
   
//        ConsultationPatientDTO cp = new ConsultationPatientDTO(
//          "20735758-d837-40d1-bd6e-a520e1c44866",
//          "2063af17-fb8b-4bad-8cf1-14fa784273e2",
//          "1000.55",
//          "False",
//          "C:\\Users\\nsalazar\\Documents\\therapistapp\\patients\\2063af17-fb8b-4bad-8cf1-14fa784273e2\\notes\\group"
//         );
//        
//        ConsultationPatientService cps = new ConsultationPatientService();
//        
//        ConsultationPatientController cpc = new ConsultationPatientController(cps);
        
//        cps.insertConsultationPatient(cp);
        
//        List<ConsultationPatientDTO> consultationPatients = cps.getPatientsByConsultationId("e941c0ef-2d0c-4b4b-a0e2-0a5e3cf0e278");
//        
//        System.out.println(consultationPatients.toString());

//        cps.deleteConsultationPatient("e941c0ef-2d0c-4b4b-a0e2-0a5e3cf0e278", "ca23bc0c-b62b-4e67-aa75-3446a62a4d7e");
        
//        cps.setConsultationPatientPaid("e941c0ef-2d0c-4b4b-a0e2-0a5e3cf0e278", "ca23bc0c-b62b-4e67-aa75-3446a62a4d7e");

//        List<PatientDTO> patients = cpc.getPatientsByConsultationId("20735758-d837-40d1-bd6e-a520e1c44866");
//        
//        System.out.println(patients.toString());
        

    }
    
}
