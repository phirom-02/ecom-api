package com.firom.ecom_api.module.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateProductDto(
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String description,
        @NotNull @Positive Double price
) {}
