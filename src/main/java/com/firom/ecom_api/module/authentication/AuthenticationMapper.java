package com.firom.ecom_api.module.authentication;


import com.firom.ecom_api.module.authentication.dto.SigninResponseDto;
import com.firom.ecom_api.module.authentication.dto.SignupResponseDto;
import com.firom.ecom_api.module.user.CustomUserDetails;

public class AuthenticationMapper {

    public static SigninResponseDto toLoginResponseDto(String accessToken, String refreshToken, CustomUserDetails userDetails) {
        return new SigninResponseDto(accessToken, refreshToken, userDetails);
    }

    public static SignupResponseDto toSignupResponseDto(String accessToken, String refreshToken, String verifyToken, CustomUserDetails userDetails) {
        return new SignupResponseDto(accessToken, refreshToken, verifyToken, userDetails);
    }
}
