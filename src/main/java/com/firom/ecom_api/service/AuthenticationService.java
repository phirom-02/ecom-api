package com.firom.ecom_api.service;

import com.firom.ecom_api.dto.authentication.AuthenticationResponseDto;
import com.firom.ecom_api.dto.authentication.LoginUserDto;
import com.firom.ecom_api.dto.authentication.RefreshTokenResponseDto;
import com.firom.ecom_api.dto.authentication.SignupUserDto;

public interface AuthenticationService {

    AuthenticationResponseDto signup(SignupUserDto signupUserDto);

    AuthenticationResponseDto login(LoginUserDto loginUserDto);

    void signout(String refreshToken);

    void signoutAllDevices(String refreshToken);

    RefreshTokenResponseDto refresh(String refreshToken);
}
