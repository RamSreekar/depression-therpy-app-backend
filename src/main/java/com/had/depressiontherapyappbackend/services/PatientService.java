package com.had.depressiontherapyappbackend.services;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.had.depressiontherapyappbackend.entities.Mood;

@Repository
public interface PatientService {
    
    public ResponseEntity<?> getAllPatientsList();
    
    public ResponseEntity<?> getPatientMedicalHistory(int patientId);

    public ResponseEntity<?> getAssignmentList(int patientId);

    public ResponseEntity<?> setPatientMood(JsonNode request);

    public ResponseEntity<?> getMoodList(int patientId);

    public ResponseEntity<?> updateFcmToken(int patientId, JsonNode request);

    public ResponseEntity<?> getCurrFcmToken(int patientId);
}
