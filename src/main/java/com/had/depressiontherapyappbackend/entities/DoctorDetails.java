package com.had.depressiontherapyappbackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.print.Doc;
import javax.websocket.OnError;

@Entity
@Table(name = "doctor_details")
public class DoctorDetails {

    @Id
    @Column(name = "id")
    private int doctorDetailsId;

    @OneToOne
    @MapsId
    @JsonBackReference
    private Doctor doctor;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "experience_in_years")
    private int experienceInYears;
}
