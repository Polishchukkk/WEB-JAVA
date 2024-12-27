package com.example.demo.service;

import com.example.demo.domain.Product;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {
    private final Map<UUID, Product> productRepository = new HashMap<>();

    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepository.values());
    }

    public Product getProductById(UUID id) {
        return Optional.ofNullable(productRepository.get(id))
                .orElseThrow(() ->
                        new NoSuchElementException(String.format("Product with ID %s not found", id)));
    }

    public Product saveProduct(Product product) {
        product.setId(UUID.randomUUID());
        productRepository.put(product.getId(), product);
        return product;
    }

    public Product updateProduct(UUID id, Product product) {
        if (!productRepository.containsKey(id)) {
            throw new NoSuchElementException("Product with ID " + id + " not found for update");
        }
        product.setId(id);
        productRepository.put(id, product);
        return product;
    }

    public void deleteProduct(UUID id) {
        if (!productRepository.containsKey(id)) {
            throw new NoSuchElementException("Product with ID " + id + " not found for deletion");
        }
        productRepository.remove(id);
    }
}
