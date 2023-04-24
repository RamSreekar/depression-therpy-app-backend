package com.had.depressiontherapyappbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.had.depressiontherapyappbackend.serviceImpl.DoctorServiceImpl;

@RestController
@RequestMapping(path = "/doctors")
public class DoctorController {

    private DoctorServiceImpl doctorServiceImpl;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorServiceImpl) {
        this.doctorServiceImpl = doctorServiceImpl;
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping(path = "/{doctorId}/patients")
    public ResponseEntity<?> getPatientListOfDoctor(@PathVariable("doctorId") int doctorId) {
        return doctorServiceImpl.getPatientIdListofDoctor(doctorId);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping(path = "/{doctorId}/doctor-details")
    public ResponseEntity<?> getDoctorDetails(@PathVariable("doctorId") int doctorId) {
        return doctorServiceImpl.getDoctorDetails(doctorId);
    }

    @GetMapping(path = "/get-all")
    public ResponseEntity<?> getAllDoctorsList() {
        return doctorServiceImpl.getAllDoctorsList();
    }

}
