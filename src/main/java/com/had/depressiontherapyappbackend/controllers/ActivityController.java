package com.had.depressiontherapyappbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.had.depressiontherapyappbackend.serviceImpl.ActivityServiceImpl;

@RestController
@RequestMapping(path = "/activities")
public class ActivityController {
    
    private ActivityServiceImpl activityServiceImpl;

    public ActivityController(ActivityServiceImpl activityServiceImpl) {
        this.activityServiceImpl = activityServiceImpl;
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'ADMIN')")
    @GetMapping(path = "")
    public ResponseEntity<?> getAllActivities() {
        return activityServiceImpl.getAllActivities();
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'PATIENT')")
    @GetMapping(path = "/{activityId}/questions")
    public ResponseEntity<?> getAllQuestionsOfOneActivity(@PathVariable("activityId") int activityId) {
        return activityServiceImpl.getAllQuestionsOfOneActivity(activityId);
    }

}
