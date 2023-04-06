package com.had.depressiontherapyappbackend.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.had.depressiontherapyappbackend.entities.Activity;
import com.had.depressiontherapyappbackend.entities.Item;
import com.had.depressiontherapyappbackend.entities.Question;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.repositories.ActivityRepo;
import com.had.depressiontherapyappbackend.services.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {
    
    private ActivityRepo activityRepo;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @Autowired
    public ActivityServiceImpl(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }

    @Override
    public ResponseEntity<?> getAllActivities() {
        List<Activity> activitiesList = activityRepo.findAll();

        return new ResponseEntity<>(
            new ApiResponse(true, "", activitiesList),
            HttpStatus.OK
        ); 
    }

    @Override
    public ResponseEntity<?> getAllQuestionsOfOneActivity(int activityId) {

        ResponseEntity responseEntity = this.itemServiceImpl.getItemUsingId(activityId);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int httpStatusCode = httpStatus.value();
        
        if(httpStatusCode != 200) 
            return responseEntity;

        Item requiredItem = (Item) this.itemServiceImpl.getItemUsingId(activityId).getBody();

        Activity activity = requiredItem.getActivity();

        List<Question> questionList = activity.getQuestionList(); 

        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

}
