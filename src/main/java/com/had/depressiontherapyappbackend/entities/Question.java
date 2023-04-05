package com.had.depressiontherapyappbackend.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import net.bytebuddy.agent.builder.AgentBuilder.InitializationStrategy.SelfInjection.Eager;

@Entity
@Table(name = "question")
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id")
    private int questionId;

    @Column(name = "question_title")
    private String question;

    @Column(name = "options")
    private String options;

    @ManyToOne(targetEntity = Activity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    
}
