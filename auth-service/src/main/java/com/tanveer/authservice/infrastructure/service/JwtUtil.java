package com.tanveer.authservice.infrastructure.service;

import com.tanveer.authservice.infrastructure.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Clock;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final long jwtExpiration;
    private final long refreshExpiration;
    private final Key secretKey;
    private final Clock clock;

    public JwtUtil(@Value("${security.jwt.secret-key}") String secret, @Value("${security.jwt.expiration}")
    Long jwtExpiration, @Value("${security.jwt.refresh-token.expiration}") Long refreshExpiration, Clock clock) {
        this.clock = clock;
        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.jwtExpiration = jwtExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token).getPayload();
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(clock.millis() + jwtExpiration)) // 10 hours
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(clock.millis() + refreshExpiration)) // 10 hours
                .signWith(secretKey)
                .compact();
    }

    public void validateToken(String token) throws CustomException {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new CustomException("Token expired");
        } catch (JwtException e) {
            throw new CustomException("Invalid JWT token");
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        try {
            Claims claims = extractAllClaims(token);
            String username = claims.getSubject();
            Date expiration = claims.getExpiration();

            boolean usernameValid = username.equals(userDetails.getUsername());
            boolean notExpired = expiration.after(new Date());

            return usernameValid && notExpired;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }
}
