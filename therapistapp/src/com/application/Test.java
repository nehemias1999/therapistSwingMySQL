/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.application;

import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dao.PatientDAO;
import com.application.model.dto.PatientDTO;
import com.application.model.entities.Patient;
import com.application.services.PatientService;
import com.application.utils.PatientsFilesManager;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author nsalazar
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ValidationException, BusinessException {
        Patient patient = new Patient(
                UUID.fromString("d6dea4d2-71be-4549-a6ad-f4b6a72be5ab"),
                "12345690", 
                "juan martin", 
                "martinez", 
                LocalDate.parse("1986-02-13"),
                "maestro",
                "12345678", 
                "jmartinez@gmail.com", 
                UUID.fromString("6172c6f4-f7b0-4f8a-96ab-2d8bacb06f08"), 
                "moreno",
                25,
                0,
                ""
        );
        PatientDAO patientDAO = new PatientDAO();
        
        patientDAO.updatePatient(patient);

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
        
    }
    
}
