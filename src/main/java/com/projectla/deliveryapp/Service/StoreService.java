package com.projectla.deliveryapp.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projectla.deliveryapp.Entity.Store;
import com.projectla.deliveryapp.Repository.StoreRepository;

@Service
public class StoreService {

    private final StoreRepository repository;

    public StoreService(StoreRepository repository) {
        this.repository = repository;
    }

    public Store create(Store store) {
        return repository.save(store);
    }

    public Store getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found"));
    }

    public List<Store> getAllStores() {
        return repository.findAll();    
    }
}

