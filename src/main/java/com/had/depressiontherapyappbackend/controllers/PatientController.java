package com.had.depressiontherapyappbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.had.depressiontherapyappbackend.serviceImpl.PatientServiceImpl;

@RestController
@RequestMapping(path = "/patient")
public class PatientController {
    
    private PatientServiceImpl patientServiceImpl;

    public PatientController(PatientServiceImpl patientServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
    }

    @GetMapping(path = "/{patientId}/medical_history")
    public ResponseEntity<?> getPatientMedicalHistory(@PathVariable("patientId") int patientId) {
        return patientServiceImpl.getPatientMedicalHistory(patientId);
    }

}
