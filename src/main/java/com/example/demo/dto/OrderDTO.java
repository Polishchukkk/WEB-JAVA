package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private UUID orderId;

    @NotNull
    private List<ProductDTO> productList;

    @NotNull
    @Positive
    private Double totalPrice;

    @NotNull
    private LocalDate orderDate;
}
