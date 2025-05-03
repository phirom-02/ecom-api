package com.firom.ecom_api.module.authentication.dto;

public record RefreshTokenResponseDto(
        String accessToken,
        String refreshToken
) {}
