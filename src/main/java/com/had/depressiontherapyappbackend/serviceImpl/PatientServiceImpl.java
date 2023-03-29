package com.had.depressiontherapyappbackend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.had.depressiontherapyappbackend.entities.Patient;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.repositories.PatientRepo;
import com.had.depressiontherapyappbackend.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
    
    private PatientRepo patientRepo;

    @Autowired
    public PatientServiceImpl(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    @Override
    public ResponseEntity<?> getPatientMedicalHistory(int patientId) {
        Patient patient = (Patient) patientRepo.findById(patientId).get();

        if(patient == null) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Patient with given ID doesn't exist", null)
                    , HttpStatus.OK
            );
        }


        return new ResponseEntity<>(
                    new ApiResponse(false, "User with given ID doesn't exist", patient.getMedicalHistory())
                    , HttpStatus.OK
            );
    }

}
