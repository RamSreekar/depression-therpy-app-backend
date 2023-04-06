package com.had.depressiontherapyappbackend.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityService {
    
    public ResponseEntity<?> getAllActivities();

    public ResponseEntity<?> getAllQuestionsOfOneActivity(int activityId);

}
