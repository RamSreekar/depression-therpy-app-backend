package com.had.depressiontherapyappbackend.services;

import com.had.depressiontherapyappbackend.entities.Doctor;
import com.had.depressiontherapyappbackend.entities.User;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminService {

    public ResponseEntity<?> createAdmin(User user);

    public ResponseEntity<?> registerDoctor(Doctor doctor) throws Exception; 
    
    public ResponseEntity<?> assignDoctorToPatient(int patientId, int doctorId);
}