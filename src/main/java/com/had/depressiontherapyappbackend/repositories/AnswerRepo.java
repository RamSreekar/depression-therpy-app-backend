package com.had.depressiontherapyappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.had.depressiontherapyappbackend.entities.Answer;

@Repository
public interface AnswerRepo extends JpaRepository<Answer, Integer> {
    
    //public Answer findByPatientIdAndQuestionId(int patientId, int questionId);

}
