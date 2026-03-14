package com.projectla.deliveryapp.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String description; 
    private String imageUrl;

    @Column(nullable = false)
    private Integer stock = 0;


    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
