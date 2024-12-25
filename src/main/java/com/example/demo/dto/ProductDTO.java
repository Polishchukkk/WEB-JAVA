package com.example.demo.dto;

import com.example.demo.validation.CosmicWordCheck;
import jakarta.validation.constraints.*;
import java.util.UUID;

public class ProductDTO {
    private UUID id;

    @NotNull
    @Size(min = 3, max = 50)
    @CosmicWordCheck  // Додаємо кастомну валідацію до поля name
    private String name;

    @NotNull
    @Positive
    private Double price;

    private String description;

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
