package com.had.depressiontherapyappbackend.entities;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    private List<Question> questionList;

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", name='" + name + '\'' +
                ", description='" + description + 
                '}';
    }

}
