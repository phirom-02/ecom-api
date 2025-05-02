package com.firom.ecom_api.dto.authentication;

public record RefreshTokenResponseDto(
        String accessToken,
        String refreshToken
) {}
