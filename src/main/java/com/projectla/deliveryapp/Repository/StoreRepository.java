package com.projectla.deliveryapp.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.projectla.deliveryapp.Entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
    
}
