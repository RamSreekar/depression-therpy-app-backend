package com.had.depressiontherapyappbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.had.depressiontherapyappbackend.entities.Item;
import com.had.depressiontherapyappbackend.serviceImpl.ItemServiceImpl;

@RestController
@RequestMapping(path = "/item")
public class ItemController {
    
    private ItemServiceImpl itemServiceImpl;

    @Autowired
    public ItemController(ItemServiceImpl itemServiceImpl) {
        this.itemServiceImpl = itemServiceImpl;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createItem(@RequestBody Item item) {
        return itemServiceImpl.createItem(item);
    }

}
