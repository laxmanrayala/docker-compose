package com.projectla.deliveryapp.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projectla.deliveryapp.Entity.Product;
import com.projectla.deliveryapp.Entity.Store;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStoreId(Long storeId);
    
    Page<Product> findByStore(Store store, Pageable pageable);


}

