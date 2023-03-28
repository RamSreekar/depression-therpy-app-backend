package com.had.depressiontherapyappbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.had.depressiontherapyappbackend.serviceImpl.DoctorServiceImpl;

@RestController
@RequestMapping(path = "/doctor")
public class DoctorController {

    private DoctorServiceImpl doctorServiceImpl;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorServiceImpl) {
        this.doctorServiceImpl = doctorServiceImpl;
    }

    @GetMapping(path = "/{doctorId}/getPatientList")
    public ResponseEntity<?> getPatientListOfDoctor(@PathVariable("doctorId") int doctorId) {
        return doctorServiceImpl.getPatientListOfDoctor(doctorId);
    }

}
