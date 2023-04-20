package com.had.depressiontherapyappbackend.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.had.depressiontherapyappbackend.serviceImpl.AnswerServiceImpl;

@RestController
@RequestMapping(path = "/answers")
public class AnswerController {
    
    private AnswerServiceImpl answerServiceImpl;

    @Autowired
    public AnswerController(AnswerServiceImpl answerServiceImpl) {
        this.answerServiceImpl = answerServiceImpl;
    }

    @PostMapping(path = "/save")
    public ResponseEntity<?> saveAnswers(@RequestBody Map<String, Object> request) {
        return answerServiceImpl.saveAnswers(request);
    }

    @GetMapping(path = "/get-all")
    public ResponseEntity<?> getAllAnswers() {
        return answerServiceImpl.getAllAnswers();
    }
}
