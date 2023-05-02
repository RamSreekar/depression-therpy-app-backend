package com.had.depressiontherapyappbackend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.had.depressiontherapyappbackend.entities.Doctor;
import com.had.depressiontherapyappbackend.entities.Patient;
import com.had.depressiontherapyappbackend.entities.User;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.payloads.ResponseUser;
import com.had.depressiontherapyappbackend.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/users")
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PATIENT', 'DOCTOR')")
    @GetMapping(path = "/get/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") int userId) {
        ApiResponse apiResponse = (ApiResponse) userServiceImpl.getUser(userId).getBody();
        User user = (User) apiResponse.getResponse();
        if(user == null) {
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        
        ResponseUser responseUser = UserServiceImpl.userToResponseUserMapper(user);
        
        return new ResponseEntity<>(responseUser, HttpStatus.OK);
    }

    @PostMapping(path = "/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestBody User user) {
        return userServiceImpl.checkEmail(user);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> userLogin(@RequestBody User user) {
        return userServiceImpl.login(user);
    }

    @PostMapping(path = "/add-patient")
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) throws Exception {
        return userServiceImpl.addPatient(patient);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PATIENT')")
    @PostMapping(path = "/register-doctor")
    public ResponseEntity<?> registerPatient(Doctor doctor) throws Exception {
        return userServiceImpl.registerDoctor(doctor);
    }

    @PreAuthorize("hasAnyAuthority('PATIENT', 'DOCTOR', 'ADMIN')")
    @GetMapping(path = "/{email}")
    public ResponseEntity<?> getUserFromEmail(@PathVariable("email") String email) {
        System.out.println("\n"+email+"\n");
        return userServiceImpl.getUserFromEmail(email);
    }

    @GetMapping(path = "/{userId}/demographics")
    public ResponseEntity<?> getDemographicsOfUser(@PathVariable("userId") int userId) {
        return userServiceImpl.getDemographicsOfUser(userId);
    }
    
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PATIENT')")
    @GetMapping(path = "/get-doctors")
    public ResponseEntity<?> getAllDoctors() {
        return userServiceImpl.getAllDoctors();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PATIENT', 'DOCTOR')")
    @PutMapping(path = "/change-password")
    public ResponseEntity<?> changePassword(@RequestBody JsonNode request) {
        return userServiceImpl.changePassword(request);
    }
}
