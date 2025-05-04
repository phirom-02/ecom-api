package com.firom.ecom_api.module.authentication;

import com.firom.ecom_api.common.enums.TokenType;
import com.firom.ecom_api.common.exceptions.InvalidTokenException;
import com.firom.ecom_api.module.authentication.dto.SigninResponseDto;
import com.firom.ecom_api.module.authentication.dto.SigninDto;
import com.firom.ecom_api.module.authentication.dto.RefreshTokenResponseDto;
import com.firom.ecom_api.module.authentication.dto.SignupResponseDto;
import com.firom.ecom_api.module.product.RefreshToken;
import com.firom.ecom_api.module.user.CustomUserDetails;
import com.firom.ecom_api.module.user.User;
import com.firom.ecom_api.module.user.UserMapper;
import com.firom.ecom_api.module.user.UserService;
import com.firom.ecom_api.module.user.dto.SaveUserDto;
import com.firom.ecom_api.security.JwtService;
import com.firom.ecom_api.module.refreshToken.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationServiceImpl(
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtService jwtService,
            RefreshTokenService  refreshTokenService
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public SignupResponseDto signup(SaveUserDto saveUserDto) {
        // Save user
        User savedUser = userService.saveUser(saveUserDto);
        // Convert to CustomUserDetails object
        CustomUserDetails userDetails = UserMapper.usertoCustomUserDetails(savedUser);

        // Generate a new refresh token
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(savedUser);
        String refreshToken = newRefreshToken.getToken();
        // Generate a new access token
        String accessToken = jwtService.generateAccessToken(userDetails);
        // Generate a new token for user verification
        String verifyToken = jwtService.generateToken(TokenType.VERIFY, userDetails);

        return AuthenticationMapper.toSignupResponseDto(accessToken, refreshToken, verifyToken, userDetails);
    }

    @Override
    public void verifyAccount(String token) {
        // Check if token has type of VERIFY
        TokenType type = jwtService.extractTokenType(token);
        if(type != TokenType.VERIFY) {
            throw new InvalidTokenException();
        }
        String username = jwtService.extractUsername(token);

        // Get user from DB
        User user = userService.findUserByUsername(username);
        // Verify the user
        userService.verifyUser(user);
    }

    @Override
    public SigninResponseDto login(SigninDto signinDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signinDto.username(),
                        signinDto.password()
                )
        );

        // Find user by email
        User receivedUser = userService.findUserByEmail(signinDto.email());

        // Convert to CustomUserDetails object
        CustomUserDetails userDetails = UserMapper.usertoCustomUserDetails(receivedUser);

        // Generate a new refresh token
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(receivedUser);
        String refreshToken = newRefreshToken.getToken();
        // Generate a new access token
        String accessToken = jwtService.generateAccessToken(userDetails);

        return AuthenticationMapper.toLoginResponseDto(accessToken, refreshToken, userDetails);
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
        // Check if token has type of REFRESH
        TokenType type = jwtService.extractTokenType(token);
        if(type != TokenType.REFRESH) {
            throw new InvalidTokenException();
        }
        // Validate the refresh token (check expiry and revocation)
        RefreshToken receivedToken = refreshTokenService.findRefreshTokenByToken(token);

        // Get the user associated with the refresh token
        User user = receivedToken.getUser();
        CustomUserDetails userDetails = UserMapper.usertoCustomUserDetails(user);

        // Rotate the refresh token (invalidate the old one and generate a new one)
        RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(receivedToken, user);
        String refreshToken = newRefreshToken.getToken();
        // Generate a new accessToken
        String accessToken = jwtService.generateAccessToken(userDetails);

        return new RefreshTokenResponseDto(accessToken, refreshToken);
    }
}
