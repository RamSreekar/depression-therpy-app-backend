package com.had.depressiontherapyappbackend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.had.depressiontherapyappbackend.entities.Patient;
import com.had.depressiontherapyappbackend.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
    public ResponseEntity<?> createUser(User user);

    public ResponseEntity<?> getUser(int userId);

    public ResponseEntity<?> checkEmail(User user);

    public ResponseEntity<?> addPatient(Patient patient) throws Exception;
}
