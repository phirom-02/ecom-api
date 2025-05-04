package com.firom.ecom_api.module.authentication.dto;


import com.firom.ecom_api.module.user.CustomUserDetails;
import com.firom.ecom_api.module.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninResponseDto {
   private String accessToken;
   private String refreshToken;
   private UserDto user;

    public SigninResponseDto(
            String accessToken,
            String refreshToken,
            CustomUserDetails userDetails
    ) {
        User user = userDetails.user();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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
