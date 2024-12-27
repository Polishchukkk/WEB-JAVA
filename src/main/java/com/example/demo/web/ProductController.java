package com.example.demo.web;

import com.example.demo.dto.ProductDTO;
import com.example.demo.domain.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts()
                .stream()
                .map(productMapper::productToProductDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        return productMapper.productToProductDTO(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        Product savedProduct = productService.saveProduct(product);
        ProductDTO responseDTO = productMapper.productToProductDTO(savedProduct);
        log.info("Created Product: {}", responseDTO); // Use the logger here
        return responseDTO;
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        Product updatedProduct = productService.updateProduct(id, product);
        log.info("Updated Product: {}", updatedProduct); // Use the logger here
        return productMapper.productToProductDTO(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
