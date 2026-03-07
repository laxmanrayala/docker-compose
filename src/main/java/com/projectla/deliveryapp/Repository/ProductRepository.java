package com.projectla.deliveryapp.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projectla.deliveryapp.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStoreId(Long storeId);

}

