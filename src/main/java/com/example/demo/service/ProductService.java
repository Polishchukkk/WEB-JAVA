package com.example.demo.service;

import com.example.demo.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(UUID id);

    Product saveProduct(Product product);

    Product updateProduct(UUID id, Product product);

    void deleteProduct(UUID id);

    String fetchExternalData();
}
