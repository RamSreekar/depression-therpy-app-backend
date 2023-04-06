package com.had.depressiontherapyappbackend.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionService {
    
    public ResponseEntity<?> getQuestionById(int questionId);

}
