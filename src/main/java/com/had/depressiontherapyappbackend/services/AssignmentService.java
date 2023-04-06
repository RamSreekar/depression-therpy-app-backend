package com.had.depressiontherapyappbackend.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;

@Repository
public interface AssignmentService {
    
    public ResponseEntity<?> createAssignment(JsonNode request);

    public ResponseEntity<?> addListOfAssignmnets(List<JsonNode> requestList);

    public ResponseEntity<?> markAssignmentAsCompleted(int activityId);

}
