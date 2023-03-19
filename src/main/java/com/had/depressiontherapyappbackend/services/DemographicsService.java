package com.had.depressiontherapyappbackend.services;

import com.had.depressiontherapyappbackend.entities.Demographics;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DemographicsService {
    public ResponseEntity<?> addDemographics(Demographics demographics);

    public ResponseEntity<?> getDemographics(int userId);
}
