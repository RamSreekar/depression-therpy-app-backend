package com.had.depressiontherapyappbackend.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.had.depressiontherapyappbackend.entities.Patient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mood")
@Data
@NoArgsConstructor
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int moodId;

    @Column(name = "mood_value")
    private int moodValue;

    @Column(name = "timeStamp")
    private String timeStamp;

    @ManyToOne(targetEntity = Patient.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;
    
}
