package com.firom.ecom_api.dto.product;

public record ProductDto (
    Integer id,
    String name,
    String description,
    Double price
) {}
