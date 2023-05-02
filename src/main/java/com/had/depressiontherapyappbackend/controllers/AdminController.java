package com.had.depressiontherapyappbackend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.had.depressiontherapyappbackend.entities.Doctor;
import com.had.depressiontherapyappbackend.entities.Question;
import com.had.depressiontherapyappbackend.entities.User;
import com.had.depressiontherapyappbackend.serviceImpl.AdminServiceImpl;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private AdminServiceImpl adminServiceImpl;

    public AdminController() {
        
    }

    @Autowired
    public AdminController(AdminServiceImpl adminServiceImpl) {
        this.adminServiceImpl = adminServiceImpl;
    }
 
    @PostMapping(path = "/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        return adminServiceImpl.createAdmin(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/register-doctor")
    public ResponseEntity<?> registerDoctor(@RequestBody Doctor doctor) throws Exception {
        return adminServiceImpl.registerDoctor(doctor);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PATIENT')")
    @PostMapping(path = "/assignDoctorToPatient")
    public ResponseEntity<?> assignDoctorToPatient(@RequestBody JsonNode request) {
        int patientId = request.get("patientId").asInt();
        int doctorId = request.get("doctorId").asInt();
        return adminServiceImpl.assignDoctorToPatient(patientId, doctorId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/add-question")
    public ResponseEntity<?> addQuestion(@RequestBody Question question) {
        return adminServiceImpl.addQuestion(question);
    }

}
