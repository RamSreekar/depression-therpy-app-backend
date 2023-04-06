package com.had.depressiontherapyappbackend.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.had.depressiontherapyappbackend.entities.Item;
import com.had.depressiontherapyappbackend.entities.Admin;
import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.repositories.AdminRepo;
import com.had.depressiontherapyappbackend.repositories.ItemRepo;
import com.had.depressiontherapyappbackend.services.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
    
    private ItemRepo itemRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    public ItemServiceImpl(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    public ResponseEntity<?> getItemUsingId(int itemId) {
        Optional<Item> queryResponse = itemRepo.findById(itemId);

        if(queryResponse.isEmpty()) {
            return new ResponseEntity<>("Item with given ID doesn't exist", HttpStatus.NOT_FOUND);
        }

        Item requiredItem = (Item) queryResponse.get();

        return new ResponseEntity<>(requiredItem, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createItem(Item item) {
        int adminId = item.getAdmin().getAdminId();
        Admin requiredAdmin = (Admin) this.adminRepo.findById(adminId).get();

        item.setAdmin(requiredAdmin);

        itemRepo.save(item);

        return new ResponseEntity<>(
            new ApiResponse(true, "", null),
            HttpStatus.OK
        ); 
    }

}
