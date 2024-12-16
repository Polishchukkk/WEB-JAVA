package com.example.demo.service;

import com.example.demo.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @Test
    void testSaveProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(19.99);
        product.setDescription("This is a test product.");

        Product savedProduct = productService.saveProduct(product);

        assertNotNull(savedProduct.getId(), "Product ID should not be null after saving.");
        assertEquals("Test Product", savedProduct.getName(), "Product name should match.");
        assertEquals(19.99, savedProduct.getPrice(), "Product price should match.");
        assertEquals("This is a test product.", savedProduct.getDescription(), "Product description should match.");
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(10.0);
        productService.saveProduct(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(20.0);
        productService.saveProduct(product2);

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size(), "There should be two products in the list.");
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(15.99);
        Product savedProduct = productService.saveProduct(product);

        UUID id = savedProduct.getId();
        Product foundProduct = productService.getProductById(id);

        assertNotNull(foundProduct, "Product should be found by ID.");
        assertEquals(id, foundProduct.getId(), "Product IDs should match.");
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setName("Product to Update");
        product.setPrice(50.0);
        Product savedProduct = productService.saveProduct(product);

        UUID id = savedProduct.getId();
        product.setName("Updated Product");
        product.setPrice(60.0);

        Product updatedProduct = productService.updateProduct(id, product);

        assertEquals("Updated Product", updatedProduct.getName(), "Product name should be updated.");
        assertEquals(60.0, updatedProduct.getPrice(), "Product price should be updated.");
        assertEquals(id, updatedProduct.getId(), "Product ID should remain the same.");
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setName("Product to Delete");
        product.setPrice(25.0);
        Product savedProduct = productService.saveProduct(product);

        UUID id = savedProduct.getId();
        productService.deleteProduct(id);

        Product deletedProduct = productService.getProductById(id);
        assertNull(deletedProduct, "Product should be null after deletion.");
    }
}
