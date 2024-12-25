package com.example.demo.service;

import com.example.demo.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService productService;
    private Product product;

    @BeforeEach
    void setUp() {
        productService = new ProductService();

        product = new Product();
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setDescription("Sample description");
    }

    @Test
    void testSaveProduct() {
        Product savedProduct = productService.saveProduct(product);

        assertNotNull(savedProduct.getId(), "Product ID should not be null");
        assertEquals("Test Product", savedProduct.getName());
        assertEquals(100.0, savedProduct.getPrice());
    }

    @Test
    void testGetAllProducts() {
        productService.saveProduct(product);
        List<Product> products = productService.getAllProducts();

        assertEquals(1, products.size(), "There should be one product");
    }

    @Test
    void testGetProductById() {
        Product savedProduct = productService.saveProduct(product);
        UUID id = savedProduct.getId();

        Product foundProduct = productService.getProductById(id);
        assertEquals(savedProduct, foundProduct, "Found product should match saved product");
    }

    @Test
    void testGetProductByIdNotFound() {
        UUID randomId = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> productService.getProductById(randomId),
                "Expected exception when product is not found"
        );
        assertTrue(exception.getMessage().contains("Product with ID " + randomId + " not found"));
    }

    @Test
    void testUpdateProduct() {
        Product savedProduct = productService.saveProduct(product);
        UUID id = savedProduct.getId();

        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Name");
        updatedProduct.setPrice(200.0);
        updatedProduct.setDescription("Updated description");

        Product result = productService.updateProduct(id, updatedProduct);
        assertEquals("Updated Name", result.getName());
        assertEquals(200.0, result.getPrice());
        assertEquals(id, result.getId());
    }

    @Test
    void testUpdateProductNotFound() {
        UUID randomId = UUID.randomUUID();
        Product updatedProduct = new Product();
        updatedProduct.setName("Non-existing");
        updatedProduct.setPrice(300.0);

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> productService.updateProduct(randomId, updatedProduct)
        );
        assertTrue(exception.getMessage().contains("not found for update"));
    }

    @Test
    void testDeleteProduct() {
        Product savedProduct = productService.saveProduct(product);
        UUID id = savedProduct.getId();

        productService.deleteProduct(id);
        assertThrows(
                NoSuchElementException.class,
                () -> productService.getProductById(id),
                "Product should be deleted and not found"
        );
    }

    @Test
    void testDeleteProductNotFound() {
        UUID randomId = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> productService.deleteProduct(randomId)
        );
        assertTrue(exception.getMessage().contains("not found for deletion"));
    }
}
