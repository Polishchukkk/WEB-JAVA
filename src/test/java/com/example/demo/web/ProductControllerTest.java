package com.example.demo.web;

import com.example.demo.service.ProductService;
import com.example.demo.web.ProductController;
import com.example.demo.domain.Product;
import com.example.demo.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    private ProductService productService; // Мокуємо ProductService

    @InjectMocks
    private ProductController productController; // Інжекція моків в контролер

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Ініціалізація моків
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testGetAllProducts() throws Exception {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Test Product");
        product.setPrice(29.99);
        when(productService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].price").value(29.99));
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Star Product");  // Тепер це ім'я містить космічний термін
        productDTO.setPrice(19.99);

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Star Product");
        product.setPrice(19.99);

        when(productService.saveProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content("{\"name\":\"Star Product\",\"price\":19.99}"))
                .andExpect(status().isCreated())  // Тепер очікуємо 201 Created
                .andExpect(jsonPath("$.name").value("Star Product"))
                .andExpect(jsonPath("$.price").value(19.99));
    }
}
