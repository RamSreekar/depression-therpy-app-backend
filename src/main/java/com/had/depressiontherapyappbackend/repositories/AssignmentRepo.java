package com.had.depressiontherapyappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.had.depressiontherapyappbackend.entities.Assignment;

public interface AssignmentRepo extends JpaRepository<Assignment, Integer> {
    
}
