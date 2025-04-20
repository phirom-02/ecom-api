package com.firom.ecom_api.dto.product;

import java.util.Optional;

public record UpdateProductDto(
        Optional<String> name,
        Optional<String> description,
        Optional<Double> price
) {
}
