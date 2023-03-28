package com.had.depressiontherapyappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.had.depressiontherapyappbackend.entities.Patient;


@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {
     
}
