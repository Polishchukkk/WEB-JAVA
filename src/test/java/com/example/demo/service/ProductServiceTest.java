package com.example.demo.service;

import com.example.demo.service.ProductService; // Add the correct import
import com.example.demo.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ProductService.class})
class ProductServiceTest {

    @Autowired
    private ProductService productService;  // Autowire ProductService bean

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setDescription("Sample description");
    }

    @Test
    void testSaveProduct() {
        Product savedProduct = productService.saveProduct(product);
        assertNotNull(savedProduct.getId());
        assertEquals("Test Product", savedProduct.getName());
    }

    @Test
    void testGetAllProducts() {
        productService.saveProduct(product);
        List<Product> products = productService.getAllProducts();
        assertEquals(3, products.size());
    }

    @Test
    void testGetProductById() {
        Product savedProduct = productService.saveProduct(product);
        UUID id = savedProduct.getId();
        Product foundProduct = productService.getProductById(id);
        assertEquals(savedProduct, foundProduct);
    }

    @Test
    void testGetProductByIdNotFound() {
        UUID randomId = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> productService.getProductById(randomId)
        );
        assertTrue(exception.getMessage().contains("Product with ID " + randomId + " not found"));
    }
}
