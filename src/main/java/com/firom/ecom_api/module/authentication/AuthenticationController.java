package com.firom.ecom_api.module.authentication;

import com.firom.ecom_api.common.dto.ApiResponse;
import com.firom.ecom_api.module.authentication.dto.SigninResponseDto;
import com.firom.ecom_api.module.authentication.dto.SigninDto;
import com.firom.ecom_api.module.authentication.dto.RefreshTokenResponseDto;
import com.firom.ecom_api.handler.ApiResponseHandler;
import com.firom.ecom_api.module.authentication.dto.SignupResponseDto;
import com.firom.ecom_api.module.user.dto.SaveUserDto;
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
    public ResponseEntity<ApiResponse<SignupResponseDto>> register(@RequestBody @Valid SaveUserDto saveUserDto) {
        SignupResponseDto registeredUser = authenticationService.signup(saveUserDto);
        return ApiResponseHandler.created(registeredUser);
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<Object>> verifySignup(@RequestParam("token") String token) {
        authenticationService.verifyAccount(token);
        return ApiResponseHandler.success(null);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<SigninResponseDto>> signin(@RequestBody @Valid SigninDto loginUserDto) {
        SigninResponseDto authenticatedUser = authenticationService.login(loginUserDto);
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
