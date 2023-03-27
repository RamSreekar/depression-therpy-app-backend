package com.had.depressiontherapyappbackend.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Entity
@Table(name = "admin")
public class Admin {
    
    @Id
    @Column(name = "id")
    private int adminId;

    @OneToOne
    @MapsId
    @JsonBackReference
    private User user;

    public int getAdminId() {
        return this.adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}