package com.had.depressiontherapyappbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.had.depressiontherapyappbackend.entities.Mood;
import com.had.depressiontherapyappbackend.serviceImpl.PatientServiceImpl;

@RestController
@RequestMapping(path = "/patients")
// @PreAuthorize("hasAuthority('PATIENT', DOCTOR)")
public class PatientController {
    
    private PatientServiceImpl patientServiceImpl;

    public PatientController(PatientServiceImpl patientServiceImpl) {
        this.patientServiceImpl = patientServiceImpl;
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'PATIENT')")
    @GetMapping(path = "/{patientId}/medical-history")
    public ResponseEntity<?> getPatientMedicalHistory(@PathVariable("patientId") int patientId) {
        return patientServiceImpl.getPatientMedicalHistory(patientId);
    }

    @GetMapping(path = "/get-all")
    public ResponseEntity<?> getAllPatientsList() {
        return patientServiceImpl.getAllPatientsList();
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'PATIENT')")
    @GetMapping(path = "/{patientId}/assignments")
    public ResponseEntity<?> getAssignmentList(@PathVariable("patientId") int patientId) {
        return patientServiceImpl.getAssignmentList(patientId);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping(path = "/{patientId}/mood")
    public ResponseEntity<?> getMoodList(@PathVariable("patientId") int patientId) {
        return patientServiceImpl.getMoodList(patientId);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping(path = "/set-mood")
    public ResponseEntity<?> setPatientMood(@RequestBody JsonNode request) {
        return patientServiceImpl.setPatientMood(request);
    }

    @GetMapping(path = "/{patientId}/answers")
    public ResponseEntity<?> getAnswerList(@PathVariable("patientId") int patientId) {
        return patientServiceImpl.getAnswerList(patientId);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'PATIENT')")
    @GetMapping(path = "/{patientId}/answers/{questionId}")
    public ResponseEntity<?> getAnswerWithPatientIdAndDoctorId(@PathVariable("patientId") int patientId, 
                                            @PathVariable("questionId") int questionId) {
        return patientServiceImpl.getAnswerWithPatientIdAndQuestionId(patientId, questionId);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PutMapping(path = "/{patientId}/fcm-token")
    public ResponseEntity<?> updateFcmToken(@PathVariable("patientId") int patientId, @RequestBody JsonNode request) {
        return patientServiceImpl.updateFcmToken(patientId, request);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping(path = "/{patientId}/fcm-token")
    public ResponseEntity<?> getCurrFcmToken(@PathVariable("patientId") int patientId) {
        return patientServiceImpl.getCurrFcmToken(patientId);
    }
}
