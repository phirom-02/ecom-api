package com.firom.ecom_api.dto.product;

public record AddProductDto(
        String name,
        String description,
        double price
) {}
