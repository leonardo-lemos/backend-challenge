package dev.leonardolemos.backendchallenge.model.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class ProductDTO {
    private long id;
    private String name;

    public ProductDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
