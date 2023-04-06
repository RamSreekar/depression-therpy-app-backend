package com.had.depressiontherapyappbackend.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "item")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private int itemId;

    @Column(name = "type")
    private String type;

    @ManyToOne(targetEntity = Admin.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private Activity activity;

    // @Override
    // public String toString() {
    //     return "Item{" +
    //             "itemId=" + itemId +
    //             ", type='" + type + '\'' +
    //             ", adminId='" + admin.getAdminId() + 
    //             '}';
    // }

}
