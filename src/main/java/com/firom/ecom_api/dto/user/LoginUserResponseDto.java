package com.firom.ecom_api.dto.user;

public record LoginUserResponseDto (
        String token,
        long expiresIn
) {
}
