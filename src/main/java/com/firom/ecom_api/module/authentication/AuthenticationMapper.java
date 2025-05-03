package com.firom.ecom_api.module.authentication;


import com.firom.ecom_api.module.authentication.dto.AuthenticationResponseDto;
import com.firom.ecom_api.module.user.User;
import com.firom.ecom_api.security.CustomUserDetails;

public class AuthenticationMapper {

    public static AuthenticationResponseDto toAuthenticationResponse(String accessToken, String refreshToken, CustomUserDetails userDetails) {
        return new AuthenticationResponseDto(accessToken, refreshToken, userDetails);
    }

    public static CustomUserDetails toCustomUserDetails(User user) {
        return new CustomUserDetails(user);
    }
}
