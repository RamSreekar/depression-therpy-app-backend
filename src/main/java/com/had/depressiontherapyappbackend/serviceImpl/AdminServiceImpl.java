package com.had.depressiontherapyappbackend.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.had.depressiontherapyappbackend.entities.Activity;
import com.had.depressiontherapyappbackend.entities.Admin;
import com.had.depressiontherapyappbackend.entities.Doctor;
import com.had.depressiontherapyappbackend.entities.Item;
import com.had.depressiontherapyappbackend.entities.Patient;
import com.had.depressiontherapyappbackend.entities.Question;
import com.had.depressiontherapyappbackend.entities.Role;
import com.had.depressiontherapyappbackend.entities.User;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.payloads.ResponseUser;
import com.had.depressiontherapyappbackend.repositories.ActivityRepo;
import com.had.depressiontherapyappbackend.repositories.AdminRepo;
import com.had.depressiontherapyappbackend.repositories.DoctorRepo;
import com.had.depressiontherapyappbackend.repositories.ItemRepo;
import com.had.depressiontherapyappbackend.repositories.PatientRepo;
import com.had.depressiontherapyappbackend.repositories.QuestionRepo;
import com.had.depressiontherapyappbackend.repositories.UserRepo;
import com.had.depressiontherapyappbackend.services.AdminService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    private AdminRepo adminRepo;

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    public AdminServiceImpl(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    @Override
    public ResponseEntity<?> createAdmin(User user) {
        ResponseEntity<?> createUserResponse = this.userServiceImpl.createUser(user);
        ApiResponse createUserApiResponse = (ApiResponse) createUserResponse.getBody();
        boolean isUserCreated = createUserApiResponse.getSuccess();

        // if(!isUserCreated) {
        //     return new ResponseEntity<>(
        //         new ApiResponse(false, "User could not be created", null)
        //         , HttpStatus.OK
        //     );
        // }

    
        //ResponseUser responseUserObj = (ResponseUser) createUserApiResponse.getResponse();
        //User responseObj = UserServiceImpl.responseUserToUserMapper(responseUserObj);
        User responseObj = (User) createUserApiResponse.getResponse();
        int userId = responseObj.getUserId();

        User reqUser = userRepo.findById(userId).get();

        Admin admin = user.getAdmin();
        admin.setAdminId(userId);
        admin.setUser(reqUser);
        reqUser.setAdmin(admin);

        this.userRepo.save(reqUser);

        System.out.println("\n"+reqUser.toString()+"\n");

        return new ResponseEntity<>(
                new ApiResponse(true, "User created", null)
                , HttpStatus.OK
        );
    }
 

    @Override
    public ResponseEntity<?> registerDoctor(Doctor doctor) throws Exception {
        int userId = doctor.getDoctorId();

        System.out.println(doctor);
        System.out.println("\n"+userId+"\n");

        ResponseEntity<?> res = this.userServiceImpl.getUser(userId);
        ApiResponse apiResponse = (ApiResponse) res.getBody();
        User user = (User) apiResponse.getResponse();

        //User user = this.userRepo.findById(userId).get();

        if(user == null) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "User with given Id doesn't exist", null)
                    , HttpStatus.NOT_FOUND
            );
        }

        doctor.setUser(user);
        user.setDoctor(doctor);
        userRepo.save(user);

        return new ResponseEntity<>(
                new ApiResponse(true, "Patient details added.", null)
                , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> assignDoctorToPatient(int patientId, int doctorId) {
        ResponseEntity<?> patientResponseEntity = this.userServiceImpl.getUser(patientId);
        ApiResponse patientApiResponse = (ApiResponse) patientResponseEntity.getBody();
        boolean patientExists = patientApiResponse.getSuccess();

        if(!patientExists) {
            return patientResponseEntity;
        }

        User requiredPatientUser = (User) patientApiResponse.getResponse();
        Patient requiredPatient = requiredPatientUser.getPatient();


        ResponseEntity<?> doctorResponseEntity = this.userServiceImpl.getUser(doctorId);
        ApiResponse doctorApiResponse = (ApiResponse) doctorResponseEntity.getBody();
        boolean doctorExists = doctorApiResponse.getSuccess();

        if(!doctorExists) {
            return doctorResponseEntity;
        }

        User requiredDoctorUser = (User) doctorApiResponse.getResponse();
        Doctor requiredDoctor = requiredDoctorUser.getDoctor();
    
        requiredPatient.setDoctor(requiredDoctor);
        this.patientRepo.save(requiredPatient);

        return new ResponseEntity<>(
                new ApiResponse(true, "Doctor assigned to patient.", null)
                , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> addQuestion(Question request) {
        int activityId = request.getActivity().getActivityId();
        Item requiredItem = this.itemRepo.findById(activityId).get();

        System.out.println("\nItem : ");
        System.out.println(requiredItem);
        System.out.println();

        Activity requiredActivity = requiredItem.getActivity();

        request.setActivity(requiredActivity);

        this.questionRepo.save(request);

        return new ResponseEntity<>("Question added!", HttpStatus.OK);
    }
}
