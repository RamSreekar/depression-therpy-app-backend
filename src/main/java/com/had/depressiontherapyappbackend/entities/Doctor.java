package com.had.depressiontherapyappbackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @Column(name = "id")
    private int doctorId;

    @Column(name = "admin_id")
    private int adminId;

    @OneToOne
    @MapsId
    @JsonBackReference
    private User user;

    @ManyToOne(targetEntity = Admin.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Admin admin;

    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "id")
    @JsonManagedReference
    private DoctorDetails doctorDetails;

}
