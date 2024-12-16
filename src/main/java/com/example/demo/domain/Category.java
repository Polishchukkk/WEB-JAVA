package com.example.demo.domain;

import jakarta.validation.constraints.*;
import java.util.UUID;

public class Category {
    private UUID id;

    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    public Category() {
        this.id = UUID.randomUUID();
    }

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
}

