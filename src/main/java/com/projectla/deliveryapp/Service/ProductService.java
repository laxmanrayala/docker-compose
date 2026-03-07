package com.projectla.deliveryapp.Service;

import org.springframework.stereotype.Service;

import com.projectla.deliveryapp.Entity.Product;
import com.projectla.deliveryapp.Entity.Store;
import com.projectla.deliveryapp.Repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final StoreService storeService;

    public ProductService(ProductRepository repository, StoreService storeService) {
        this.repository = repository;
        this.storeService = storeService;
    }

    public Product addProduct(Long storeId, Product product) {
        Store store = storeService.getById(storeId);
        product.setStore(store);
        return repository.save(product);
    }

    public List<Product> getProductsByStore(Long storeId) {
        return repository.findByStoreId(storeId);
    }
}

