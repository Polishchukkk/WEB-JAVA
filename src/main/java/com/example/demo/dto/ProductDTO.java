package com.example.demo.dto;

import com.example.demo.validation.CosmicWordCheck;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private UUID id;

    @NotNull
    @Size(min = 3, max = 50)
    @CosmicWordCheck  // Add custom validation to the name field
    private String name;

    @NotNull
    @Positive
    private Double price;

    private String description;
}
