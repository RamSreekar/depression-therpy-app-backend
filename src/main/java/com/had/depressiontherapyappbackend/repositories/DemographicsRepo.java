package com.had.depressiontherapyappbackend.repositories;

import com.had.depressiontherapyappbackend.entities.Demographics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemographicsRepo extends JpaRepository<Demographics, Integer> {
}
