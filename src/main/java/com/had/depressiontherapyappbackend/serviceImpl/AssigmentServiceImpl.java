package com.had.depressiontherapyappbackend.serviceImpl;

import java.util.List;
import java.util.Optional;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.had.depressiontherapyappbackend.entities.Assignment;
import com.had.depressiontherapyappbackend.entities.Doctor;
import com.had.depressiontherapyappbackend.entities.Item;
import com.had.depressiontherapyappbackend.entities.Patient;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.repositories.AssignmentRepo;
import com.had.depressiontherapyappbackend.services.AssignmentService;

@Service
public class AssigmentServiceImpl implements AssignmentService {

    private AssignmentRepo assignmentRepo;

    @Autowired
    private PatientServiceImpl patientServiceImpl;

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @Autowired
    public AssigmentServiceImpl(AssignmentRepo assignmentRepo) {
        this.assignmentRepo = assignmentRepo;
    }
    
    @Override 
    public ResponseEntity<?> createAssignment(JsonNode request) {
        int patientId = request.get("patient_id").asInt();
        int doctorId = request.get("doctor_id").asInt();
        int itemId = request.get("item_id").asInt();
        int itemLevel = request.get("item_level").asInt();

        ResponseEntity<?> responseEntity = this.patientServiceImpl.getPatientUsingId(patientId);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        boolean successStatus = apiResponse.getSuccess();

        if(successStatus == false) {
            return responseEntity;
        }

        Patient requiredPatient = (Patient) apiResponse.getResponse();

        responseEntity = this.doctorServiceImpl.getDoctorUsingId(doctorId);
        apiResponse = (ApiResponse) responseEntity.getBody();
        successStatus = apiResponse.getSuccess();

        if(successStatus = false) {
            return responseEntity;
        }

        Doctor requiredDoctor = (Doctor) apiResponse.getResponse();

        responseEntity = this.itemServiceImpl.getItemUsingId(itemId);
        apiResponse = (ApiResponse) responseEntity.getBody();
        successStatus = apiResponse.getSuccess();

        if(successStatus = false) {
            return responseEntity;
        }

        Item requiredItem = (Item) apiResponse.getResponse();

        Assignment assignment = new Assignment();
        assignment.setPatient(requiredPatient);
        assignment.setDoctor(requiredDoctor);
        assignment.setItem(requiredItem);
        assignment.setItemLevel(itemLevel);

        assignmentRepo.save(assignment);

        return new ResponseEntity<>(
            new ApiResponse(true, "Assignment created!", null),
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> addListOfAssignmnets(List<JsonNode> requestList) {
        JsonNode request;
        for(int i=0; i<requestList.size(); i++) {
            request = requestList.get(i);
            createAssignment(request);
        }

        //get this particular patient device's fcmToken from db and use in setToken() fn
//        FirebaseMessaging.getInstance().sendAsync(Message.builder()
//                .setNotification(new Notification("Title", "Message"))
//                .setToken("<device registration token>")
//                .build());


        return new ResponseEntity<>(
            new ApiResponse(true, "Assignment created!", null),
            HttpStatus.OK
        );
    }

    private ResponseEntity<?> getAssignmentById(int assignmentId) {
        Optional<Assignment> queryResponse = assignmentRepo.findById(assignmentId);

        if(queryResponse.isEmpty()) {
            return new ResponseEntity<>("Invalid Assignment Id!", HttpStatus.NOT_FOUND);
        }

        Assignment requiredAssignment = queryResponse.get();

        System.out.println("\nAssignment :");
        System.out.println(requiredAssignment);
        System.out.println();

        return new ResponseEntity<>(requiredAssignment, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> markAssignmentAsCompleted(int assignmentId) {
        ResponseEntity responseEntity = getAssignmentById(assignmentId);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int httpStatusCode = httpStatus.value();
        
        if(httpStatusCode != 200) 
            return responseEntity;

        Assignment requiredAssignment = (Assignment) responseEntity.getBody();
        requiredAssignment.setCompleted(true);

        assignmentRepo.save(requiredAssignment);

        return new ResponseEntity<>("Assignment marked as completed!", HttpStatus.OK);
    }
}
