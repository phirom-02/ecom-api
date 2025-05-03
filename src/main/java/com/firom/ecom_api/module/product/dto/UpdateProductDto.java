package com.firom.ecom_api.module.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.Optional;

public record UpdateProductDto(
        @NotBlank Optional<String> name,
        @NotBlank Optional<String> description,
        @Positive  Optional<Double> price
) {
}
