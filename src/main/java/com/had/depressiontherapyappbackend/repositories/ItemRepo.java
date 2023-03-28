package com.had.depressiontherapyappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.had.depressiontherapyappbackend.entities.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item, Integer> {
    
}
