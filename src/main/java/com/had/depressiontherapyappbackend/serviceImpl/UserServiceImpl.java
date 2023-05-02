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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

    private String encryptPassword(String password) {
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		String encryptedPassword = pwdEncoder.encode(password);
		pwdEncoder = null;

        return encryptedPassword;
    }


    @Override
    public ResponseEntity<?> createUser(User user) {
        ResponseEntity<?> emailCheck = checkEmail(user);
        int statusCode = emailCheck.getStatusCode().value();
        if(statusCode != 200) return emailCheck;

        int roleId = user.getUserRole().getRoleId();
        Role userRole =  (Role) this.roleRepo.findById(roleId).get();

        System.out.println();
        System.out.println(user);
        System.out.println();

        String encryptedPassword = encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);

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
        
        return new ResponseEntity<>(Map.of("userId", responseObj.getUserId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUser(int userId) {
        Optional<User> res = userRepo.findById(userId);

        if(res.isEmpty()) {
            System.out.println("User doesn't exist! - 1");
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

        if(res.size() != 0)
            return new ResponseEntity<>(Map.of("message", "Email exists!"), HttpStatus.FOUND);
        else
            return new ResponseEntity<>(Map.of("token", "randomtokenjassudurgajwt"), HttpStatus.OK);
            //apiResponse = new ApiResponse(false, "Email already exists!", Map.of("token", "randomtokenjassudurgajwt"));

    }

    public ResponseEntity<?> login(User user) {
        System.out.println();
        System.out.println(user);
        System.out.println();
        ApiResponse apiResponse;
        List<User> res = userRepo.findByEmail(user.getEmail());

        if(res.size() == 0) {
            apiResponse = new ApiResponse(false, "Email doesn't exist!", null);
            //return new ResponseEntity<>(apiResponse, HttpStatus.FA);
        }
        else
            apiResponse = new ApiResponse(true, "Email exists!", Map.of("token", "randomtokenjassudurgajwt"));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserFromEmail(String email) {
        ApiResponse apiResponse;
        List<User> res = userRepo.findByEmail(email);

        if(res.size() == 0) {
            return new ResponseEntity<>(Map.of("message", "User doesn't exist with given email!"), HttpStatus.NOT_FOUND);
        }
        
        User user = res.get(0);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getDemographicsOfUser(int userId) {
        Optional<User> res = userRepo.findById(userId);

        if(res.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "User with given ID doesn't exist", null)
                    , HttpStatus.OK
            );
        }

        User reqUser = res.get();

        return new ResponseEntity<>(
                new ApiResponse(true, "", reqUser.getDemographics())
                , HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> changePassword(JsonNode credentials) {
        String email = credentials.get("email").asText();
        String rawPassword = credentials.get("oldPassword").asText();
        String newPassword = credentials.get("newPassword").asText();

        ResponseEntity response = getUserFromEmail(email);
        if(response.getStatusCode().value() != 200) {
            return response;
        }

        User user = (User) response.getBody();
        String userPasswordEncoded = user.getPassword();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean passwordsMatching = bCryptPasswordEncoder.matches(rawPassword, userPasswordEncoded);
        if(!passwordsMatching) {
            return new ResponseEntity<>(Map.of("message", "Password incorrect!"), HttpStatus.FORBIDDEN);
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepo.save(user);

        return new ResponseEntity<>(Map.of("message", "Password has been changed!"), HttpStatus.OK);
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

    public ResponseEntity<?> getAllDoctors() {
        int DOCTOR_ROLE_ID = 2;
        List<UserRepo.UserProjection> doctorList = userRepo.findByRoleId(DOCTOR_ROLE_ID);

        return new ResponseEntity<>(doctorList, HttpStatus.OK);
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


