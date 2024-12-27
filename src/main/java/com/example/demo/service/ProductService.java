package com.example.demo.service;

import com.example.demo.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class ProductService {

    private final Map<UUID, Product> productRepository = new HashMap<>();
    private final WebClient webClient;

    public ProductService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build(); // WireMock працює на порту 8080
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepository.values());
    }

    public Product getProductById(UUID id) {
        return Optional.ofNullable(productRepository.get(id))
                .orElseThrow(() -> new NoSuchElementException(String.format("Product with ID %s not found", id)));
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

    // Інтеграція з REST API (через WebClient)
    public String fetchExternalData() {
        return webClient.get()
                .uri("/api/v1/products") // Це тепер буде звернення до заглушки WireMock
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

