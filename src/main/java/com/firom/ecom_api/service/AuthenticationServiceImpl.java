package com.firom.ecom_api.service;

import com.firom.ecom_api.common.enums.TokenType;
import com.firom.ecom_api.common.exceptions.InvalidTokenException;
import com.firom.ecom_api.dto.authentication.AuthenticationResponseDto;
import com.firom.ecom_api.dto.authentication.LoginUserDto;
import com.firom.ecom_api.dto.authentication.RefreshTokenResponseDto;
import com.firom.ecom_api.dto.authentication.SignupUserDto;
import com.firom.ecom_api.mapper.AuthenticationMapper;
import com.firom.ecom_api.model.RefreshToken;
import com.firom.ecom_api.model.User;
import com.firom.ecom_api.repository.UserRepository;
import com.firom.ecom_api.security.CustomUserDetails;
import com.firom.ecom_api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RefreshTokenService  refreshTokenService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public AuthenticationResponseDto signup(SignupUserDto signupUserDto) {
        // Create a new User object
        User user = new User();
        user.setUsername(signupUserDto.username());
        user.setPassword(passwordEncoder.encode(signupUserDto.password()));
        user.setEmail(signupUserDto.email());

        // Save user
        User savedUser = userRepository.save(user);
        // Convert to CustomUserDetails object
        CustomUserDetails userDetails = AuthenticationMapper.toCustomUserDetails(savedUser);

        // Generate a new refresh token
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(savedUser);
        String refreshToken = newRefreshToken.getToken();
        // Generate a new access token
        String accessToken = jwtService.generateAccessToken(userDetails);

        return AuthenticationMapper.toAuthenticationResponse(accessToken, refreshToken, userDetails);
    }

    @Override
    public AuthenticationResponseDto login(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.username(),
                        loginUserDto.password()
                )
        );

        // Find user by email
        var user = userRepository.findUserByEmail(loginUserDto.email())
                .orElseThrow(() -> new UsernameNotFoundException(loginUserDto.email()));
        // Convert to CustomUserDetails object
        var userDetails = AuthenticationMapper.toCustomUserDetails(user);

        // Generate a new refresh token
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);
        String refreshToken = newRefreshToken.getToken();
        // Generate a new access token
        String accessToken = jwtService.generateAccessToken(userDetails);

        return AuthenticationMapper.toAuthenticationResponse(accessToken, refreshToken, userDetails);
    }

    @Override
    public void signout(String refreshToken) {
        // Received RefreshToken object from DB
        RefreshToken receivedRefreshToken = refreshTokenService.findRefreshTokenByToken(refreshToken);
        // Revoke the current refreshToken
        refreshTokenService.revokeToken(receivedRefreshToken);
    }

    @Override
    public void signoutAllDevices(String refreshToken) {
        // Received RefreshToken object from DB
        RefreshToken receivedRefreshToken = refreshTokenService.findRefreshTokenByToken(refreshToken);
        // Get extract user from receivedRefreshToken object
        User user = receivedRefreshToken.getUser();
        // Revoke all token associated with the current
        refreshTokenService.revokeAllByUser(user);
    }

    @Override
    public RefreshTokenResponseDto refresh(String token) {
        // Check if token has type of
        TokenType type = jwtService.extractTokenType(token);
        if(type != TokenType.REQUEST) {
            throw new InvalidTokenException();
        }
        // Validate the refresh token (check expiry and revocation)
        RefreshToken receivedToken = refreshTokenService.findRefreshTokenByToken(token);

        // Get the user associated with the refresh token
        User user = receivedToken.getUser();
        var userDetails = AuthenticationMapper.toCustomUserDetails(user);

        // Rotate the refresh token (invalidate the old one and generate a new one)
        RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(receivedToken, user);
        String refreshToken = newRefreshToken.getToken();
        // Generate a new accessToken
        String accessToken = jwtService.generateAccessToken(userDetails);

        return new RefreshTokenResponseDto(accessToken, refreshToken);
    }
}
