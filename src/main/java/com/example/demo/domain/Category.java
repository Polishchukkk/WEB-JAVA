package com.example.demo.domain;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Category {
    private UUID id;

    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    public Category() {
        this.id = UUID.randomUUID();
    }

}

