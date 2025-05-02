package com.firom.ecom_api.service;

import com.firom.ecom_api.common.enums.TokenStatus;
import com.firom.ecom_api.common.exceptions.InvalidTokenException;
import com.firom.ecom_api.model.RefreshToken;
import com.firom.ecom_api.model.User;
import com.firom.ecom_api.repository.RefreshTokenRepository;
import com.firom.ecom_api.repository.UserRepository;
import com.firom.ecom_api.security.CustomUserDetails;
import com.firom.ecom_api.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refresh-token-expiration-time}")
    private long refreshTokenExpirationTime;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtService jwtService;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    @Override
    public RefreshToken createRefreshToken(User user) {
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateRefreshToken(userDetails);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpirationTime));
        refreshToken.setStatus(TokenStatus.ACTIVE);

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken findRefreshTokenByToken(String token) {

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(InvalidTokenException::new);
    }

    private void verifyTokenStatus(RefreshToken token) {
        // Check token expiration date
        if (token.getExpiryDate().isBefore(Instant.now())) {
            token.setStatus(TokenStatus.EXPIRED);
            refreshTokenRepository.save(token);
            throw new InvalidTokenException();
        }

        // Check token status
        if (token.getStatus() == TokenStatus.REVOKED || token.getStatus() == TokenStatus.EXPIRED) {
            throw new InvalidTokenException();
        }
    }

    @Override
    public RefreshToken rotateRefreshToken(RefreshToken oldToken, User user) {
        // verify the old token
        verifyTokenStatus(oldToken);

        // Revoke the old token
        revokeToken(oldToken);

        // Create a new token
        return createRefreshToken(user);
    }

    @Override
    public void revokeToken(RefreshToken token) {
        // Verify token status
        token.setStatus(TokenStatus.REVOKED);
        refreshTokenRepository.save(token);
    }

    @Override
    public void revokeAllByUser(User user) {
        List<RefreshToken> tokens = refreshTokenRepository.findAllByUser(user);
        tokens.forEach(token -> token.setStatus(TokenStatus.REVOKED));
    }
}
