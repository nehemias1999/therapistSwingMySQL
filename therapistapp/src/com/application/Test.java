/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.application;

import com.application.controllers.entities.ControllerRegistry;
import com.application.exceptions.businessException.BusinessException;
import com.application.exceptions.businessException.ValidationException;
import com.application.model.dto.ConsultationDTO;
import com.application.services.ConsultationPatientService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nsalazar
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ValidationException, BusinessException, IOException {
//        ControllerRegistry cr = new ControllerRegistry();
//        
//        ConsultationDTO c = new ConsultationDTO(
//                "28b7f46e-eb7f-40e5-8d5b-f9bef5ea4b10",
//                "2025-10-06",
//                "09:00:00",
//                "10:00:00",
//                "2000.00",
//                "SCHEDULED"
//        );
//        
//        cr.getConsultationController().insertConsultation(c);
//        
        ConsultationPatientService cps = new ConsultationPatientService();
        
        List<String> cpid = new ArrayList<>();
        
        cpid.add("ca23bc0c-b62b-4e67-aa75-3446a62a4d7e");
        cpid.add("c2d92a63-a07f-47b5-8995-bf582773d97b");
        cpid.add("bca53da8-4e50-41dc-ab77-b73ca61fcc4e");
        cpid.add("87c660ca-ed31-4682-9eba-d25003ae5b3c");
        
        cps.insertConsultationPatients("28b7f46e-eb7f-40e5-8d5b-f9bef5ea4b10", cpid);      
        
    }
    
}
