package com.had.depressiontherapyappbackend.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerService {
    
    public ResponseEntity<?> saveAnswers(Map<String, Object> request);

    //public ResponseEntity<?> findAnswerByPatientIdAndQuestionId(int patientId, int questionId);
}
