package com.projectla.deliveryapp.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.projectla.deliveryapp.Entity.Store;
import com.projectla.deliveryapp.Service.StoreService;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService service;

    public StoreController(StoreService service) {
        this.service = service;
    }

    @PostMapping
    public Store create(@RequestBody Store store) {
        return service.create(store);
    }

    @GetMapping("{id}")
    public Store getById(@PathVariable Long id) {
        return service.getById(id);
    }

     @GetMapping
    public List<Store> getAllStores() {
        return service.getAllStores();
    }

}

