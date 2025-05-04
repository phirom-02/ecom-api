package com.firom.ecom_api.module.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveUserDto(
        @NotNull @NotBlank String email,
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String password
) {}
