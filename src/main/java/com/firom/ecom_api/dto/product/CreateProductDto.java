package com.firom.ecom_api.dto.product;

public record CreateProductDto(
        String name,
        String description,
        Double price
) {}
