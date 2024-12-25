package com.example.demo.domain;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class Order {
    private UUID orderId;

    @NotNull
    private List<Product> productList;

    @NotNull
    @Positive
    private Double totalPrice;

    @NotNull
    private LocalDate orderDate;

    public Order() {
        this.orderId = UUID.randomUUID();
    }

}
