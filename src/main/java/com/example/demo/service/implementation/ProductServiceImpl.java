package com.example.demo.service.implementation;

import com.example.demo.domain.Product;
import com.example.demo.service.inerfaces.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final Map<UUID, Product> productRepository = new HashMap<>();
    private final WebClient webClient;

    // Injecting the base URL from application.yml
    public ProductServiceImpl(WebClient.Builder webClientBuilder,
                              @Value("${webclient.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepository.values());
    }

    @Override
    public Product getProductById(UUID id) {
        return Optional.ofNullable(productRepository.get(id))
                .orElseThrow(() -> new NoSuchElementException(String.format("Product with ID %s not found", id)));
    }

    @Override
    public Product saveProduct(Product product) {
        product.setId(UUID.randomUUID());
        productRepository.put(product.getId(), product);
        return product;
    }

    @Override
    public Product updateProduct(UUID id, Product product) {
        if (!productRepository.containsKey(id)) {
            throw new NoSuchElementException("Product with ID " + id + " not found for update");
        }
        product.setId(id);
        productRepository.put(id, product);
        return product;
    }

    @Override
    public void deleteProduct(UUID id) {
        try {
            if (productRepository.containsKey(id)) {
                productRepository.remove(id);
                log.info("Product with id: {} was deleted successfully.", id);
            } else {
                log.info("Product with id: {} not found for deletion (no action taken).", id);
            }
        } catch (Throwable t) {
            log.error("Failed to delete product with id: {}", id, t);
            throw t;
        }
    }

    @Override
    public String fetchExternalData() {
        return webClient.get()
                .uri("/api/v1/products")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
