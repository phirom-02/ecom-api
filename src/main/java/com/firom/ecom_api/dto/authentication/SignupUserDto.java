package com.firom.ecom_api.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupUserDto (
        @NotNull @NotBlank String email,
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String password
) {}
