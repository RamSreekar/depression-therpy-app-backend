package com.had.depressiontherapyappbackend.repositories;

import com.had.depressiontherapyappbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    List<User> findByEmail(String email);
} 
