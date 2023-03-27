package com.had.depressiontherapyappbackend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.had.depressiontherapyappbackend.entities.Doctor;
import com.had.depressiontherapyappbackend.entities.Patient;
import com.had.depressiontherapyappbackend.entities.User;
import com.had.depressiontherapyappbackend.serviceImpl.UserServiceImpl;
import com.had.depressiontherapyappbackend.services.UserService;
//import lombok.Getter;
import jdk.security.jarsigner.JarSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/user")
public class UserController {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userServiceImpl.createUser(user);
    }

    @GetMapping(path = "/get/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") int userId) {
        return userServiceImpl.getUser(userId);
    }

    @PostMapping(path = "/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestBody User user) {
        return userServiceImpl.checkEmail(user);
    }

    @PostMapping(path = "/addPatient")
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) throws Exception {
        return userServiceImpl.addPatient(patient);
    }

    @PostMapping(path = "/registerDoctor")
    public ResponseEntity<?> registerPatient(Doctor doctor) throws Exception {
        return userServiceImpl.registerDoctor(doctor);
    }

}
