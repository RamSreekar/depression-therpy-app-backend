package com.had.depressiontherapyappbackend.serviceImpl;

import org.apache.catalina.connector.Response;
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
