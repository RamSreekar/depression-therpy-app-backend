package com.had.depressiontherapyappbackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "doctor_details")
@Data
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
