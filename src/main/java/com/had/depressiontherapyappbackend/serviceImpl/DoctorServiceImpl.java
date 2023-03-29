package com.had.depressiontherapyappbackend.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.had.depressiontherapyappbackend.entities.Doctor;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.repositories.DoctorRepo;
import com.had.depressiontherapyappbackend.services.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    private DoctorRepo doctorRepo;

    @Autowired
    public DoctorServiceImpl(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    public ResponseEntity<?> getDoctorUsingId(int doctorId) {
        Optional<Doctor> queryResponse = doctorRepo.findById(doctorId);

        if(queryResponse.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Doctor with given ID doesn't exist", null)
                    , HttpStatus.OK
            );
        }

        Doctor requiredDoctor = (Doctor) queryResponse.get();

        return new ResponseEntity<>(
                new ApiResponse(true, "Doctor exists!", requiredDoctor)
                , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getPatientListOfDoctor(int doctorId) {
        Doctor requiredDoctor = (Doctor) doctorRepo.findById(doctorId).get();

        return new ResponseEntity<>(
            new ApiResponse(true, "", requiredDoctor.getPatientList()), 
            HttpStatus.OK
        );    
    }

}
