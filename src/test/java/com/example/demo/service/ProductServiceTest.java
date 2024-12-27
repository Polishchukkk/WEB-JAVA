package com.example.demo.service;

import com.example.demo.domain.Product;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ProductServiceImpl.class})
class ProductServiceTest {

    @Autowired
    private ProductServiceImpl productService;

    private Product product;

    // Запускаємо WireMockExtension, який автоматично налаштовує порт
    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance().build();

    @BeforeEach
    void setUp() {
        // Створення продукту для тестів
        product = new Product();
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setDescription("Sample description");

        // Налаштовуємо WireMock для мок-запиту
        stubFor(get(urlEqualTo("/api/v1/products"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": \"123e4567-e89b-12d3-a456-426614174000\", \"name\": \"Product 1\", \"price\": 100}]")));
    }

    @Test
    void testSaveProduct() {
        // Перевірка збереження продукту
        Product savedProduct = productService.saveProduct(product);
        assertNotNull(savedProduct.getId());
        assertEquals("Test Product", savedProduct.getName());
    }

    @Test
    void testGetAllProducts() {
        // Додаємо продукт і перевіряємо, чи він потрапив до колекції
        productService.saveProduct(product);
        List<Product> products = productService.getAllProducts();
        assertEquals(1, products.size());
    }

    @Test
    void testGetProductById() {
        // Перевірка отримання продукту за ID
        Product savedProduct = productService.saveProduct(product);
        UUID id = savedProduct.getId();
        Product foundProduct = productService.getProductById(id);
        assertEquals(savedProduct, foundProduct);
    }

    @Test
    void testGetProductByIdNotFound() {
        // Перевірка на помилку, якщо продукт не знайдено
        UUID randomId = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> productService.getProductById(randomId)
        );
        assertTrue(exception.getMessage().contains("Product with ID " + randomId + " not found"));
    }

    @Test
    void testUpdateProduct() {
        // Перевірка оновлення продукту
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
        // Перевірка на помилку, якщо продукт не знайдений для оновлення
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
        // Перевірка видалення продукту
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
        // Перевірка на помилку, якщо продукт не знайдений для видалення
        UUID randomId = UUID.randomUUID();
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> productService.deleteProduct(randomId)
        );
        assertTrue(exception.getMessage().contains("not found for deletion"));
    }

    @Test
    void testFetchExternalData() {
        // Перевірка роботи з мок-запитом через WireMock
        String response = productService.fetchExternalData();
        assertNotNull(response);
        assertTrue(response.contains("Product 1"));
    }
}
