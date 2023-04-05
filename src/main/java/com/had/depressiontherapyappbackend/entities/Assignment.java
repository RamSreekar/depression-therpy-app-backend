package com.had.depressiontherapyappbackend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "assignment")
@Data
public class Assignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assignment_id")
    private int assignmentId;

    @Column(name = "item_level")
    private int itemLevel;

    @ManyToOne //(targetEntity = Patient.class)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne //(targetEntity = Doctor.class)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne //(targetEntity = Item.class)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "completed")
    private boolean isCompleted;

}
