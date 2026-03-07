package com.projectla.deliveryapp.Controller;

import org.springframework.web.bind.annotation.*;

import com.projectla.deliveryapp.Entity.Product;
import com.projectla.deliveryapp.Service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product addProduct(
            @PathVariable Long storeId,
            @RequestBody Product product
    ) {
        return service.addProduct(storeId, product);
    }

    @GetMapping
    public List<Product> getProducts(@PathVariable Long storeId) {
        return service.getProductsByStore(storeId);
    }
}

