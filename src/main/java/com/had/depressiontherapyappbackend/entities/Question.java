package com.had.depressiontherapyappbackend.entities;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.had.depressiontherapyappbackend.utils.MapToJsonConverter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id")
    private int questionId;

    @Column(name = "question_title")
    private String question;

    @Convert(converter = MapToJsonConverter.class)
    @Column(name = "options")
    private Map<String, String> options;

    @ManyToOne(targetEntity = Activity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;
    
}
