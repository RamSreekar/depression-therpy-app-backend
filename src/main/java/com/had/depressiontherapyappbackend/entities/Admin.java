package com.had.depressiontherapyappbackend.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "admin")
public class Admin {
    
    @Id
    @Column(name = "id")
    private int adminId;

    @OneToOne
    @MapsId
    @JsonBackReference
    private User user;
}