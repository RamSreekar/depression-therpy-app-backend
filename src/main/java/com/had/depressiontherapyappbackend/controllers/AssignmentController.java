package com.had.depressiontherapyappbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.had.depressiontherapyappbackend.serviceImpl.AssigmentServiceImpl;

@RestController
@RequestMapping(path = "/assignment")
public class AssignmentController {
    

    private AssigmentServiceImpl assigmentServiceImpl;

    @Autowired
    public AssignmentController(AssigmentServiceImpl assigmentServiceImpl) {
        this.assigmentServiceImpl = assigmentServiceImpl;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createAssignment(@RequestBody JsonNode request) {
        return assigmentServiceImpl.createAssignment(request);
    } 

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(path = "")
    public ResponseEntity<?> addListOfAssignmnets(@RequestBody List<JsonNode> request) {
        return assigmentServiceImpl.addListOfAssignmnets(request);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PutMapping(path = "/{assignmentId}/mark-as-completed")
    public ResponseEntity<?> markAssignmentAsCompleted(@PathVariable("assignmentId") int assignmentId) {
        return assigmentServiceImpl.markAssignmentAsCompleted(assignmentId);
    }
}
