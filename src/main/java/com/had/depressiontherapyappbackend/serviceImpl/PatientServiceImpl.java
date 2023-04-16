package com.had.depressiontherapyappbackend.serviceImpl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.had.depressiontherapyappbackend.entities.Mood;
import com.had.depressiontherapyappbackend.entities.Patient;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.repositories.MoodRepo;
import com.had.depressiontherapyappbackend.repositories.PatientRepo;
import com.had.depressiontherapyappbackend.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
    
    private PatientRepo patientRepo;

    @Autowired
    private MoodRepo moodRepo;

    @Autowired
    public PatientServiceImpl(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }


    public ResponseEntity<?> getPatientUsingId(int patientId) {
        Optional<Patient> queryResponse = patientRepo.findById(patientId);

        if(queryResponse.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Patient with given ID doesn't exist", null)
                    , HttpStatus.OK
            );
        }

        Patient patient = (Patient) queryResponse.get();

        return new ResponseEntity<>(
                    new ApiResponse(true, "Patient exists!", patient)
                    , HttpStatus.OK
            );
    }

    @Override
    public ResponseEntity<?> getPatientMedicalHistory(int patientId) {
        Optional<Patient> queryResponse = patientRepo.findById(patientId);

        if(queryResponse.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Patient with given ID doesn't exist", null)
                    , HttpStatus.OK
            );
        }

        Patient patient = (Patient) queryResponse.get();

        return new ResponseEntity<>(
                new ApiResponse(true, "User exists", patient.getMedicalHistory())
                , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getAssignmentList(int patientId) {
        Optional<Patient> queryResponse = patientRepo.findById(patientId);

        if(queryResponse.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Patient with given ID doesn't exist", null)
                    , HttpStatus.OK
            );
        }

        Patient patient = (Patient) queryResponse.get();

        return new ResponseEntity<>(
            new ApiResponse(true, "", patient.getAssignmentList())
            , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getMoodList(int patientId) {
        Optional<Patient> queryResponse = patientRepo.findById(patientId);

        if(queryResponse.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Patient with given ID doesn't exist"), HttpStatus.NOT_FOUND);
        }

        Patient patient = (Patient) queryResponse.get();

        return new ResponseEntity<>(patient.getMoodList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> setPatientMood(JsonNode request) {
        int patientId = request.get("patientId").asInt();
        int moodValue = request.get("moodValue").asInt();
        String timeStamp = request.get("timeStamp").asText();

        Optional<Patient> queryResponse = patientRepo.findById(patientId);
        

        if(queryResponse.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Patient with given ID doesn't exist"), HttpStatus.NOT_FOUND);
        }

        Patient patient = (Patient) queryResponse.get();

        Mood patientMood = new Mood();
        patientMood.setMoodValue(moodValue);
        patientMood.setTimeStamp(timeStamp);
        patientMood.setPatient(patient);

        this.moodRepo.save(patientMood);

        return new ResponseEntity<>("Patient mood has been set!", HttpStatus.OK);
    }

}
