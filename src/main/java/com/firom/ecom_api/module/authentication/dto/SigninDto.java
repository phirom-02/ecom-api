package com.firom.ecom_api.module.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SigninDto(
        @NotBlank String username,
        @NotNull @NotBlank String email,
        @NotNull @NotBlank String password
) {
}
