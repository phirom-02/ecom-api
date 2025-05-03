package com.firom.ecom_api.module.product.dto;

public record ProductDto (
    Integer id,
    String name,
    String description,
    Double price
) {}
