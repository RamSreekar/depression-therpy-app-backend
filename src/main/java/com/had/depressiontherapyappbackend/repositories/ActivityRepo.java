package com.had.depressiontherapyappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.had.depressiontherapyappbackend.entities.Activity;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Integer> {
    
}
