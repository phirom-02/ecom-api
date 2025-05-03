package com.firom.ecom_api.module.authentication.dto;

import com.firom.ecom_api.security.CustomUserDetails;

public record AuthenticationResponseDto(
   String accessToken,
   String refreshToken,
   CustomUserDetails user
) {}
