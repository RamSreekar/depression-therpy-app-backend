package com.had.depressiontherapyappbackend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name = "mood")
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int moodId;

    @Column(name = "mood_value")
    private int moodValue;

    @Column(name = "date")
    private String date;

    private Patient patient;
    
}
