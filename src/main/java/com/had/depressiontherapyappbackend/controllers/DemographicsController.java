package com.had.depressiontherapyappbackend.controllers;

import com.had.depressiontherapyappbackend.entities.Demographics;
import com.had.depressiontherapyappbackend.serviceImpl.DemographicsServiceImpl;
import com.had.depressiontherapyappbackend.services.DemographicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/demographics")
public class DemographicsController {

    private DemographicsServiceImpl demographicsServiceImpl;

    @Autowired
    public DemographicsController(DemographicsServiceImpl demographicsServiceImpl) {
        this.demographicsServiceImpl = demographicsServiceImpl;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> addDemographics(@RequestBody Demographics demographics) {
        return demographicsServiceImpl.addDemographics(demographics);
    }

    @GetMapping(path = "/get/{userId}")
    public ResponseEntity<?> getDemographics(@PathVariable("userId") int userId) {
        return demographicsServiceImpl.getDemographics(userId);
    }
}
