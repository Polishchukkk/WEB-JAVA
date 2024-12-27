package com.example.demo.web;

import com.example.demo.web.ProductController;
import com.example.demo.dto.ProductDTO;
import com.example.demo.domain.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.implementation.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class) // Для тестування контролера
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductServiceImpl productService; // Замокати ProductServiceImpl

    @MockBean
    private ProductMapper productMapper; // Замокати ProductMapper

    @Autowired
    private MockMvc mockMvc; // MockMvc буде автоматично інжектований

    @Test
    void testGetAllProducts() throws Exception {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Test Product");
        product.setPrice(29.99);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(29.99);

        when(productService.getAllProducts()).thenReturn(List.of(product));
        when(productMapper.productToProductDTO(any(Product.class))).thenReturn(productDTO);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].price").value(29.99));
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Star Product");
        productDTO.setPrice(19.99);

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Star Product");
        product.setPrice(19.99);

        ProductDTO savedProductDTO = new ProductDTO();
        savedProductDTO.setName("Star Product");
        savedProductDTO.setPrice(19.99);

        when(productMapper.productDTOToProduct(any(ProductDTO.class))).thenReturn(product);
        when(productService.saveProduct(any(Product.class))).thenReturn(product);
        when(productMapper.productToProductDTO(any(Product.class))).thenReturn(savedProductDTO);

        mockMvc.perform(post("/api/v1/products")
                        .contentType("application/json")
                        .content("{\"name\":\"Star Product\",\"price\":19.99}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Star Product"))
                .andExpect(jsonPath("$.price").value(19.99));
    }

    @Test
    void testUpdateProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Star Product");
        productDTO.setPrice(29.99);

        Product product = new Product();
        product.setId(productId);
        product.setName("Updated Star Product");
        product.setPrice(29.99);

        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setName("Updated Star Product");
        updatedProductDTO.setPrice(29.99);

        when(productMapper.productDTOToProduct(any(ProductDTO.class))).thenReturn(product);
        when(productService.updateProduct(eq(productId), any(Product.class))).thenReturn(product);
        when(productMapper.productToProductDTO(any(Product.class))).thenReturn(updatedProductDTO);

        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType("application/json")
                        .content("{\"name\":\"Updated Star Product\",\"price\":29.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Star Product"))
                .andExpect(jsonPath("$.price").value(29.99));
    }

    @Test
    void testDeleteProduct() throws Exception {
        UUID productId = UUID.randomUUID();

        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/v1/products/{id}", productId))
                .andExpect(status().isNoContent());
    }
}
