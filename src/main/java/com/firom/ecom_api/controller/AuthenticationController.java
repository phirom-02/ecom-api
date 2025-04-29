package com.firom.ecom_api.controller;

import com.firom.ecom_api.common.dto.ApiResponse;
import com.firom.ecom_api.dto.user.LoginUserDto;
import com.firom.ecom_api.dto.user.LoginUserResponseDto;
import com.firom.ecom_api.dto.user.SignupUserDto;
import com.firom.ecom_api.handler.ApiResponseHandler;
import com.firom.ecom_api.model.User;
import com.firom.ecom_api.security.JwtService;
import com.firom.ecom_api.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody @Valid SignupUserDto signupUserDto) {
        User registeredUser = authenticationService.signup(signupUserDto);

        return ApiResponseHandler.created(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginUserResponseDto>> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.login(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        var loginResponse = new LoginUserResponseDto(jwtToken, jwtService.getExpirationTime());

        return ApiResponseHandler.success(loginResponse);
    }
}
