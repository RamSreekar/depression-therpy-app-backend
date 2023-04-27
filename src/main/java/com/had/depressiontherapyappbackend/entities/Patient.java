package com.had.depressiontherapyappbackend.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "patient")
@Data
public class Patient {
    @Id
    @Column(name = "id")
    private int patientId;

    @OneToOne 
    @MapsId
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "id")
    @JsonManagedReference
    private MedicalHistory medicalHistory;

    // @MapsId("doctor")
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Assignment> assignmentList;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Mood> moodList;

    @Column(name = "wants_doc")
    private boolean wantsDoc;
 
    @Column(name = "joining_date")
    private String joiningDate;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Answer> answerList;

    @Column(name= "fcm_token", nullable = true)
    private String fcmToken;

    // public int getPatientId() {
    //     return patientId;
    // }

    // public void setPatientId(int patientId) {
    //     this.patientId = patientId;
    // }

    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }

    // public boolean isWantsDoc() {
    //     return wantsDoc;
    // }

    // public void setWantsDoc(boolean wantsDoc) {
    //     this.wantsDoc = wantsDoc;
    // }

    // public String getJoiningDate() {
    //     return joiningDate;
    // }

    // public void setJoiningDate(String joiningDate) {
    //     this.joiningDate = joiningDate;
    // }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                //", user=" + user +
                ", wantsDoc=" + wantsDoc +
                ", joiningDate=" + joiningDate +
                ", fcmToken=" + fcmToken +
                '}';
    }
}
