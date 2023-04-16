package com.had.depressiontherapyappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.had.depressiontherapyappbackend.entities.Mood;

@Repository
public interface MoodRepo extends JpaRepository<Mood, Integer> {
    
}
