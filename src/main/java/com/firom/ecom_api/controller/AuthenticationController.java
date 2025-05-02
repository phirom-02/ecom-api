package com.firom.ecom_api.controller;

import com.firom.ecom_api.common.dto.ApiResponse;
import com.firom.ecom_api.dto.authentication.AuthenticationResponseDto;
import com.firom.ecom_api.dto.authentication.LoginUserDto;
import com.firom.ecom_api.dto.authentication.RefreshTokenResponseDto;
import com.firom.ecom_api.dto.authentication.SignupUserDto;
import com.firom.ecom_api.handler.ApiResponseHandler;
import com.firom.ecom_api.service.AuthenticationService;
import com.firom.ecom_api.util.HttpRequestPropertiesUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthenticationResponseDto>> register(@RequestBody @Valid SignupUserDto signupUserDto) {
        AuthenticationResponseDto registeredUser = authenticationService.signup(signupUserDto);
        return ApiResponseHandler.created(registeredUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<AuthenticationResponseDto>> signin(@RequestBody @Valid LoginUserDto loginUserDto) {
        AuthenticationResponseDto authenticatedUser = authenticationService.login(loginUserDto);
        return ApiResponseHandler.success(authenticatedUser);
    }

    @PostMapping("/signout")
    public ResponseEntity<ApiResponse<Object>> signout(HttpServletRequest request) {
        String refreshToken = HttpRequestPropertiesUtils.extractBearerToken(request);
        authenticationService.signout(refreshToken);
        return ApiResponseHandler.success(null);
    }

    @PostMapping("/signout-all-devices")
    public ResponseEntity<ApiResponse<Object>> signoutAllDevices(HttpServletRequest request) {
        String refreshToken = HttpRequestPropertiesUtils.extractBearerToken(request);
        authenticationService.signoutAllDevices(refreshToken);
        return ApiResponseHandler.success(null);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResponseDto>> refreshToken(HttpServletRequest request) {
        String refreshToken = HttpRequestPropertiesUtils.extractBearerToken(request);
        RefreshTokenResponseDto refreshTokenResponseDto = authenticationService.refresh(refreshToken);
        return ApiResponseHandler.success(refreshTokenResponseDto);
    }
}
