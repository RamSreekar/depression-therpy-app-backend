package com.had.depressiontherapyappbackend.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.had.depressiontherapyappbackend.repositories.AnswerRepo;
import com.had.depressiontherapyappbackend.repositories.PatientRepo;
import com.had.depressiontherapyappbackend.services.AnswerService;
import com.had.depressiontherapyappbackend.entities.Answer;
import com.had.depressiontherapyappbackend.entities.Patient;
import com.had.depressiontherapyappbackend.entities.Question;


@Service
public class AnswerServiceImpl implements AnswerService {

    private AnswerRepo answerRepo;

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    public AnswerServiceImpl(AnswerRepo answerRepo) {
        this.answerRepo = answerRepo;
    }

    private ResponseEntity<?> saveOneAnswer(Map<String, Object> request, int patientId) {
        String choice = request.get("choice").toString();
        int questionId = ((Integer) request.get("questionId")).intValue();

        ResponseEntity questionQueryResponse = this.questionServiceImpl.getQuestionById(questionId);
        if(questionQueryResponse.getStatusCodeValue() != 200) {
            return questionQueryResponse;
        }

        Question requiredQuestion = (Question) questionQueryResponse.getBody();

        Optional<Patient> patientQueryResponse = this.patientRepo.findById(patientId);

        if(patientQueryResponse.isEmpty()) {
            return new ResponseEntity<>("Invalid Patient Id!", HttpStatus.NOT_FOUND);
        }

        Patient requiredPatient = patientQueryResponse.get();

        Answer answer = new Answer();
        answer.setChoice(choice);
        answer.setPatient(requiredPatient);
        answer.setQuestion(requiredQuestion);

        answerRepo.save(answer);


        return new ResponseEntity<>("Answer saved!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveAnswers(Map<String, Object> request) {
        Integer activityId = (Integer) request.get("activityId");
        int patientId = ((Integer) request.get("patientId")).intValue();
        List<Map<String, Object>> answerList = (List<Map<String, Object>>) request.get("choices");

        // System.out.println("\nActivity Id : ");
        // System.out.println(activityId.intValue());
        // System.out.println("\nPatient Id : ");
        // System.out.println(patientId);
        // System.out.println("\nAnswers List : ");
        // System.out.println(answerList);
        // System.out.println();

        for(int i=0; i<answerList.size(); i++) {
            Map<String, Object> answerDetails = answerList.get(i);
            ResponseEntity saveOneAnswerResponse = saveOneAnswer(answerDetails, patientId);
            if(saveOneAnswerResponse.getStatusCodeValue() != 200) {
                return saveOneAnswerResponse;
            }
        }

        return new ResponseEntity<>("Answers saved!", HttpStatus.OK);
    }

    public ResponseEntity<?> getAllAnswers() {
        List<Answer> answerList = answerRepo.findAll();

        return new ResponseEntity<>(answerList, HttpStatus.OK);
    }

    // public ResponseEntity<?> findAnswerByPatientIdAndQuestionId(int patientId, int questionId) {
    //     Answer requiredAnswer = answerRepo.findByPatientIdAndQuestionId(patientId, questionId);
    //     if(requiredAnswer == null) {
    //         return new ResponseEntity<>(Map.of("message", "Answer doesn't exist with given patient id and question id"), HttpStatus.NOT_FOUND);
    //     }

    //     //Answer requiredAnswer = (Answer) queryResponse.get();

        
    //     return new ResponseEntity<>(requiredAnswer, HttpStatus.OK);
    // }
    
}
