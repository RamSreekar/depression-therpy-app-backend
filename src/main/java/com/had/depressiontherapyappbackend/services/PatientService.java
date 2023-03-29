package com.had.depressiontherapyappbackend.services;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientService {
    
    public ResponseEntity<?> getPatientMedicalHistory(int patientId);

    public ResponseEntity<?> getAssignmentList(int patientId);
}
