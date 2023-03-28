package com.had.depressiontherapyappbackend.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.had.depressiontherapyappbackend.entities.*;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.payloads.ResponseUser;
import com.had.depressiontherapyappbackend.repositories.RoleRepo;
import com.had.depressiontherapyappbackend.repositories.UserRepo;
import com.had.depressiontherapyappbackend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;
    private Object role;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public static int calculateAgeFromDob(String dobString) {
        //Calculate Age from DOB
        int calculatedAge;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        LocalDate dobDate = LocalDate.parse(dobString, formatter);
        calculatedAge = Period.between(dobDate, LocalDate.now()).getYears();

        return calculatedAge;
    }

    public static ResponseUser userToResponseUserMapper(User reqUser) {
        ResponseUser responseUserObj = new ResponseUser();

        responseUserObj.userId = reqUser.getUserId();
        responseUserObj.email = reqUser.getEmail();
        responseUserObj.userRole = reqUser.getUserRole();
        responseUserObj.demographics = reqUser.getDemographics();
        responseUserObj.patient = reqUser.getPatient();
        responseUserObj.doctor = reqUser.getDoctor();

        return responseUserObj;
    }

    public static User responseUserToUserMapper(ResponseUser responseUser) {
        User user = new User();

        user.setUserId(responseUser.userId); 
        user.setEmail(responseUser.email);
        user.setUserRole(responseUser.userRole);
        user.setDemographics(responseUser.demographics);
        user.setPatient(responseUser.patient);
        user.setDoctor(responseUser.doctor);

        return user;
    }


    @Override
    public ResponseEntity<?> createUser(User user) {
        ResponseEntity<?> emailCheck = checkEmail(user);
        ApiResponse apiResponse = (ApiResponse) emailCheck.getBody();
        boolean emailExists = apiResponse.getSuccess();
        if(!emailExists) return emailCheck;

        int roleId = user.getUserRole().getRoleId();
        Role userRole =  (Role) this.roleRepo.findById(roleId).get();

        user.setUserRole(userRole);

        String dobString = user.getDemographics().getDob();
        int age = calculateAgeFromDob(dobString);

        Demographics demographics = user.getDemographics();
        demographics.setAge(age);

        user.setDemographics(demographics);

        userRepo.save(user);

        String email = user.getEmail();
        List<User> res = userRepo.findByEmail(email);
        int userId = res.get(0).getUserId();
        
        User responseObj = res.get(0);
        // User responseObj = new User();
        // responseObj.setUserId(userId); 

        //ResponseUser responseUserObj = userToResponseUserMapper(responseObj);
        
        return new ResponseEntity<>(
                new ApiResponse(true, "User created", responseObj)
                , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getUser(int userId) {
        Optional<User> res = userRepo.findById(userId);

        if(res.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "User with given ID doesn't exist", null)
                    , HttpStatus.OK
            );
        }

        User reqUser = res.get();


        //ResponseUser responseUserObj = userToResponseUserMapper(reqUser);
    

        return new ResponseEntity<>(
                new ApiResponse(true, "", reqUser)
                , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> checkEmail(User user) {
        ApiResponse apiResponse;
        List<User> res = userRepo.findByEmail(user.getEmail());

        if(res.size() == 0)
            apiResponse = new ApiResponse(true, "Email is new", null);
        else
            apiResponse = new ApiResponse(false, "Email already exists!", null);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addPatient(Patient request) throws Exception {
        int userId = request.getPatientId();

        ResponseEntity<?> res = getUser(userId);
        ApiResponse apiResponse = (ApiResponse) res.getBody();
        User user = (User) apiResponse.getResponse();

        if(user == null) {
            return new ResponseEntity<>(
                            new ApiResponse(false, "User with given Id doesn't exist", null)
                            , HttpStatus.OK
                        );
        }

        request.setUser(user);
        user.setPatient(request);
        userRepo.save(user);

        return new ResponseEntity<>(
                        new ApiResponse(true, "Patient details added.", userToResponseUserMapper(user))
                        , HttpStatus.OK
                    );
    }

    @Override
    public ResponseEntity<?> registerDoctor(Doctor doctor) throws Exception {
        int userId = doctor.getDoctorId();

        ResponseEntity<?> res = getUser(userId);
        ApiResponse apiResponse = (ApiResponse) res.getBody();
        User user = (User) apiResponse.getResponse();

        if(user == null) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "User with given Id doesn't exist", null)
                    , HttpStatus.OK
            );
        }

        doctor.setUser(user);
        user.setDoctor(doctor);
        userRepo.save(user);

        return new ResponseEntity<>(
                new ApiResponse(true, "Patient details added.", userToResponseUserMapper(user))
                , HttpStatus.OK
        );
    }

    public void createUserWithoutApi() {
        int roleId = 3;
        User user = new User();
        Role userRole =  (Role) this.roleRepo.findById(roleId).get();
        Demographics demographics = new Demographics();

        demographics.setFirstName("User");
        demographics.setLastName("Auth");
        demographics.setDob("01/01/2000");
        demographics.setGender("M");
        demographics.setUser(null);
        demographics.setAge(0);
        //demographics.setUserId(1);


        user.setEmail("user@auth.com");
        user.setPassword("password");
        user.setUserRole(userRole);
        user.setDemographics(demographics);
        user.setDoctor(null);
        user.setPatient(null);
        user.setAdmin(null);
        //user.setUserId(1);
        
        //userRepo.save(user);

        createUser(user);
        System.out.println("\nAdded User\n");
    }

}


