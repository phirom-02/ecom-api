package com.firom.ecom_api.security;

import com.firom.ecom_api.common.enums.TokenType;
import com.firom.ecom_api.module.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service()
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.refresh-token-expiration-time}")
    private long refreshTokenExpirationTime;

    @Value("${jwt.access-token-expiration-time}")
    private long jwtAccessExpirationTime;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateAccessToken(CustomUserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("type", TokenType.ACCESS.getValue());
        return buildToken(claims, userDetails, jwtAccessExpirationTime);
    }

    @Override
    public String generateRefreshToken(CustomUserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("type", TokenType.REFRESH.getValue());
        return buildToken(claims, userDetails, refreshTokenExpirationTime);
    }

    @Override
    public String generateToken(TokenType type, CustomUserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("type", type.getValue());
        return buildToken(claims, userDetails, refreshTokenExpirationTime);
    }

    @Override
    public boolean isTokenValid(String token, CustomUserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public TokenType extractTokenType(String token) {
        final Claims claims = extractAllClaims(token);
        String var = claims.get("type", String.class);
        return TokenType.valueOf(var);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            CustomUserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
