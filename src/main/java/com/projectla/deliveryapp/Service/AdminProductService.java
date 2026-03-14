package com.projectla.deliveryapp.Service;

import com.projectla.deliveryapp.Dto.AdminProductRequest;
import com.projectla.deliveryapp.Entity.Product;
import com.projectla.deliveryapp.Entity.Store;
import com.projectla.deliveryapp.Repository.ProductRepository;
import com.projectla.deliveryapp.Repository.StoreRepository;
import com.projectla.deliveryapp.Repository.CartItemRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AdminProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CartItemRepository cartItemRepository;

    public AdminProductService(ProductRepository productRepository,
                               StoreRepository storeRepository,
                               CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Product createProduct(AdminProductRequest request) {

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setStore(store);

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, AdminProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());

        return productRepository.save(product);
    }

    // ⭐ FIXED DELETE METHOD
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // remove product from all carts first
        cartItemRepository.deleteByProductId(id);

        // then delete the product
        productRepository.delete(product);
    }

    // ⭐ STOCK UPDATE
    public Product updateStock(Long id, Integer change) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Integer currentStock = product.getStock();

        if (currentStock == null) {
            currentStock = 0;
        }

        int newStock = currentStock + change;

        if (newStock < 0) {
            newStock = 0;
        }

        product.setStock(newStock);

        return productRepository.save(product);
    }

    public Page<Product> getProducts(int page, int size) {

        return productRepository.findAll(
                PageRequest.of(page, size, Sort.by("id"))
        );
    }

    public Page<Product> getProductsByStore(Long storeId, int page, int size) {

    Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new RuntimeException("Store not found"));

    return productRepository.findByStore(
            store,
            PageRequest.of(page, size, Sort.by("id"))
    );
}
}