package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.dto.ProductDTO;

import java.util.*;

public class ProductService {
    private final Map<UUID, Product> productRepository = new HashMap<>();

    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepository.values());
    }

    public Product getProductById(UUID id) {
        return productRepository.get(id);
    }

    public Product saveProduct(Product product) {
        product.setId(UUID.randomUUID());
        productRepository.put(product.getId(), product);
        return product;
    }

    public Product updateProduct(UUID id, Product product) {
        product.setId(id);
        productRepository.put(id, product);
        return product;
    }

    public void deleteProduct(UUID id) {
        productRepository.remove(id);
    }
}