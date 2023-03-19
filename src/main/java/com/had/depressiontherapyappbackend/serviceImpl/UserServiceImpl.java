package com.had.depressiontherapyappbackend.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.had.depressiontherapyappbackend.entities.Demographics;
import com.had.depressiontherapyappbackend.entities.Patient;
import com.had.depressiontherapyappbackend.entities.Role;
import com.had.depressiontherapyappbackend.entities.User;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
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

    @Override
    public ResponseEntity<?> createUser(User user) {
        ResponseEntity<?> emailCheck = checkEmail(user);
        ApiResponse apiResponse = (ApiResponse) emailCheck.getBody();
        boolean emailExists = apiResponse.isSuccess();
        if(!emailExists) return emailCheck;

        int roleId = user.getUserRole().getRoleId();
        Role userRole =  (Role) this.roleRepo.findById(roleId).get();

        user.setUserRole(userRole);

        //Calculate Age from DOB
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String dobString = user.getDemographics().getDob();

        LocalDate dobDate = LocalDate.parse(dobString, formatter);
        int age = Period.between(dobDate, LocalDate.now()).getYears();

        Demographics demographics = user.getDemographics();
        demographics.setAge(age);

        user.setDemographics(demographics);

        userRepo.save(user);

        String email = user.getEmail();
        List<User> res = userRepo.findByEmail(email);
        int userId = res.get(0).getUserId();

        return new ResponseEntity<>(
                new ApiResponse(true, "User created", Map.of("userId" , userId))
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
                        new ApiResponse(true, "Patient details added.", user)
                        , HttpStatus.OK
                    );
    }

}

