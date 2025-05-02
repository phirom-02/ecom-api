package com.firom.ecom_api.security;

import com.firom.ecom_api.common.enums.TokenType;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

     String extractUsername(String token);

     <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

     TokenType extractTokenType(String token);

     String generateAccessToken(CustomUserDetails userDetails);

     String generateRefreshToken(CustomUserDetails userDetails);

     boolean isTokenValid(String token, CustomUserDetails userDetails);
}
