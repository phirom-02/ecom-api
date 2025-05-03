package com.firom.ecom_api.module.authentication;

import com.firom.ecom_api.module.authentication.dto.AuthenticationResponseDto;
import com.firom.ecom_api.module.authentication.dto.LoginUserDto;
import com.firom.ecom_api.module.authentication.dto.RefreshTokenResponseDto;
import com.firom.ecom_api.module.authentication.dto.SignupUserDto;

public interface AuthenticationService {

    AuthenticationResponseDto signup(SignupUserDto signupUserDto);

    AuthenticationResponseDto login(LoginUserDto loginUserDto);

    void signout(String refreshToken);

    void signoutAllDevices(String refreshToken);

    RefreshTokenResponseDto refresh(String refreshToken);
}
