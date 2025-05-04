package com.firom.ecom_api.security;

import com.firom.ecom_api.common.enums.TokenType;
import com.firom.ecom_api.module.user.CustomUserDetails;
import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface JwtService {

     String extractUsername(String token);

     <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

     TokenType extractTokenType(String token);

     String generateAccessToken(CustomUserDetails userDetails);

     String generateRefreshToken(CustomUserDetails userDetails);

     String generateToken(TokenType tokenType, CustomUserDetails userDetails);

     boolean isTokenValid(String token, CustomUserDetails userDetails);
}
