package com.firom.ecom_api.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginUserDto (
        @NotBlank String username,
        @NotNull @NotBlank String email,
        @NotNull @NotBlank String password
) {
}
