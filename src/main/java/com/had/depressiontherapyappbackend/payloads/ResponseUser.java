package com.had.depressiontherapyappbackend.payloads;

import com.had.depressiontherapyappbackend.entities.Demographics;
import com.had.depressiontherapyappbackend.entities.Doctor;
import com.had.depressiontherapyappbackend.entities.Patient;
import com.had.depressiontherapyappbackend.entities.Role;

public class ResponseUser {
    public int userId;

    public String email;

    public Role userRole;

    public Demographics demographics;

    public Patient patient;

    public Doctor doctor;

}
