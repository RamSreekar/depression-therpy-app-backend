package com.had.depressiontherapyappbackend.serviceImpl;

import com.had.depressiontherapyappbackend.entities.Demographics;
import com.had.depressiontherapyappbackend.entities.User;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.repositories.DemographicsRepo;
import com.had.depressiontherapyappbackend.services.DemographicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DemographicsServiceImpl implements DemographicsService {

    private DemographicsRepo demographicsRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    public DemographicsServiceImpl(DemographicsRepo demographicsRepo) {
        this.demographicsRepo = demographicsRepo;
    }

    @Override
    public ResponseEntity<?> addDemographics(Demographics demographics) {
        int userId = demographics.getUserId();
        User reqUser = (User) this.userServiceImpl.getUser(userId).getBody();

        demographics.setUser(reqUser);
        demographicsRepo.save(demographics);

        return new ResponseEntity<>(
                new ApiResponse(true, "Demographic data of the user added", null),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getDemographics(int userId) {
        Demographics demographics = (Demographics) demographicsRepo.findById(userId).get();

        return new ResponseEntity<>(demographics, HttpStatus.OK);
    }
}
