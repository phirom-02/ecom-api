package com.firom.ecom_api.module.authentication;

import com.firom.ecom_api.module.authentication.dto.SigninResponseDto;
import com.firom.ecom_api.module.authentication.dto.SigninDto;
import com.firom.ecom_api.module.authentication.dto.RefreshTokenResponseDto;
import com.firom.ecom_api.module.authentication.dto.SignupResponseDto;
import com.firom.ecom_api.module.user.dto.SaveUserDto;

public interface AuthenticationService {

    SignupResponseDto signup(SaveUserDto saveUserDto);

    void verifyAccount(String token);

    SigninResponseDto login(SigninDto loginUserDto);

    void signout(String refreshToken);

    void signoutAllDevices(String refreshToken);

    RefreshTokenResponseDto refresh(String refreshToken);
}
