package com.had.depressiontherapyappbackend.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "activity")
@Data
public class Activity {
    
    @Id
    @Column(name = "id")
    private int activityId;

    @OneToOne
    @MapsId
    @JsonBackReference
    private Item item;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}
