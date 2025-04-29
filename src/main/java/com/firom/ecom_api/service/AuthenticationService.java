package com.firom.ecom_api.service;

import com.firom.ecom_api.dto.user.LoginUserDto;
import com.firom.ecom_api.dto.user.SignupUserDto;
import com.firom.ecom_api.model.User;

public interface AuthenticationService {

    User signup(SignupUserDto signupUserDto);

    User login(LoginUserDto loginUserDto);
}
