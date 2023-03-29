package com.had.depressiontherapyappbackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "doctor")
@Data
public class Doctor {

    @Id
    @Column(name = "id")
    private int doctorId;
 
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

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Patient> patientList;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private List<Assignment> assignmentList;

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId=" + doctorId +
                //", user=" + user +
                // ", admin=" + admin.toString()  +
                // ", doctorDetails=" + doctorDetails.toString()  +
                // ", patientList" + patientList.toString() +
                '}';
    }

    // public int getDoctorId() {
    //     return doctorId;
    // }

    // public void setDoctorId(int doctorId) {
    //     this.doctorId = doctorId;
    // }

    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }

    // public Admin getAdmin() {
    //     return admin;
    // }

    // public void setAdmin(Admin admin) {
    //     this.admin = admin;
    // }

    // public DoctorDetails getDoctorDetails() {
    //     return doctorDetails;
    // }

    // public void setDoctorDetails(DoctorDetails doctorDetails) {
    //     this.doctorDetails = doctorDetails;
    // }
}
