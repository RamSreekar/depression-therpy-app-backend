package com.had.depressiontherapyappbackend.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.had.depressiontherapyappbackend.entities.Activity;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.repositories.ActivityRepo;
import com.had.depressiontherapyappbackend.services.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {
    
    private ActivityRepo activityRepo;

    @Autowired
    public ActivityServiceImpl(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }

    @Override
    public ResponseEntity<?> getAllActivities() {
        List<Activity> activitiesList = activityRepo.findAll();

        return new ResponseEntity<>(
            new ApiResponse(true, "", activitiesList),
            HttpStatus.OK
        ); 
    }

}
