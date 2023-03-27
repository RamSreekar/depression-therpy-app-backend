package com.had.depressiontherapyappbackend.repositories;

import com.had.depressiontherapyappbackend.entities.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {
}
