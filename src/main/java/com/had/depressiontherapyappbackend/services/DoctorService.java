package com.had.depressiontherapyappbackend.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorService {
    public ResponseEntity<?> getPatientListOfDoctor(int doctorId);
}
