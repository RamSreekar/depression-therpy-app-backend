package com.had.depressiontherapyappbackend.serviceImpl;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.had.depressiontherapyappbackend.entities.Question;
import com.had.depressiontherapyappbackend.repositories.QuestionRepo;
import com.had.depressiontherapyappbackend.services.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepo questionRepo;

    public QuestionServiceImpl(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public ResponseEntity<?> getQuestionById(int questionId) {
        Optional<Question> queryResponse = questionRepo.findById(questionId);

        if(queryResponse.isEmpty()) {
            return new ResponseEntity<>("Invalid Question ID", HttpStatus.NOT_FOUND);
        }

        Question requiredQuestion = queryResponse.get();

        return new ResponseEntity<>(requiredQuestion, HttpStatus.OK);
    }

}
