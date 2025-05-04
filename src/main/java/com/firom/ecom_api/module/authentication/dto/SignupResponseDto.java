package com.firom.ecom_api.module.authentication.dto;

import com.firom.ecom_api.module.user.CustomUserDetails;
import com.firom.ecom_api.module.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {
    private String accessToken;
    private String refreshToken;
    private String verifyToken;
    private UserDto user;

    public SignupResponseDto(
            String accessToken,
            String refreshToken,
            String verifyToken,
            CustomUserDetails userDetails
    ) {
        User user = userDetails.user();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.verifyToken = verifyToken;
        this.user = new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public static record UserDto(
            Integer id,
            String username,
            String email
    ) {}
}
