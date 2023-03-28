package com.had.depressiontherapyappbackend.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.had.depressiontherapyappbackend.entities.Item;

@Repository
public interface ItemService {
    
    public ResponseEntity<?> createItem(Item item);

}
