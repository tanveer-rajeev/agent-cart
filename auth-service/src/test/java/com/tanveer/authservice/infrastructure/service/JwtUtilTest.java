package com.tanveer.authservice.infrastructure.service;

import com.tanveer.authservice.infrastructure.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.*;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    private Clock clock;
    private JwtUtil jwtUtil;

    private final String secret = Base64.getEncoder()
                    .encodeToString("my-super-secret-key-my-super-secret-key"
                    .getBytes());

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(
                Instant.now(),
                ZoneOffset.UTC
        );
        jwtUtil = new JwtUtil(
                secret,
                1000L * 60 * 10, // 10 min
                1000L * 60 * 60 ,
                clock// 1 hour
        );
    }

    @Test
    void shouldGenerateAccessToken() {

        String token = jwtUtil.generateToken("test@gmail.com", "ADMIN");

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldExtractUsernameFromToken() {

        String token = jwtUtil.generateToken("test@gmail.com", "ADMIN");

        String username = jwtUtil.extractUsername(token);

        assertEquals("test@gmail.com", username);
    }

    @Test
    void shouldValidateCorrectToken() {

        String token = jwtUtil.generateToken("test@gmail.com", "ADMIN");

        assertDoesNotThrow(() ->
                jwtUtil.validateToken(token)
        );
    }

    @Test
    void shouldThrowExceptionForInvalidToken() {

        String invalidToken = "invalid.jwt.token";

        assertThrows(
                CustomException.class,
                () -> jwtUtil.validateToken(invalidToken)
        );
    }

    @Test
    void shouldThrowExceptionWhenTokenIsExpired() {

        Clock fixedClock = Clock.fixed(
                Instant.now(),
                ZoneId.systemDefault()
        );

        JwtUtil jwtUtilWithClock = new JwtUtil(secret, 1L, 1L, fixedClock);

        String token = jwtUtilWithClock.generateToken("test@gmail.com", "ADMIN");

        Clock laterClock = Clock.offset(fixedClock, Duration.ofSeconds(5));

        JwtUtil expiredUtil = new JwtUtil(secret, 1L, 1L, laterClock);

        CustomException ex = assertThrows(CustomException.class,
                () -> expiredUtil.validateToken(token)
        );

        assertEquals("Token expired", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsInvalid() {

        String invalidToken = "this.is.not.a.valid.token";

        CustomException ex = assertThrows(CustomException.class,
                () -> jwtUtil.validateToken(invalidToken)
        );

        assertEquals("Invalid JWT token", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionForExpireToken() {

        String invalidToken = "invalid.jwt.token";

        assertThrows(
                CustomException.class,
                () -> jwtUtil.validateToken(invalidToken)
        );
    }

    @Test
    void shouldReturnTrueWhenTokenMatchesUser() {

        String token = jwtUtil.generateToken("test@gmail.com", "ADMIN");

        UserDetails userDetails =
                User.builder()
                        .username("test@gmail.com")
                        .password("password")
                        .roles("ADMIN")
                        .build();

        boolean result = jwtUtil.isTokenValid(token, userDetails);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenUsernameDoesNotMatch() {

        String token = jwtUtil.generateToken("test@gmail.com", "ADMIN");

        UserDetails userDetails =
                User.builder()
                        .username("another@gmail.com")
                        .password("password")
                        .roles("ADMIN")
                        .build();

        boolean result = jwtUtil.isTokenValid(token, userDetails);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenTokenExpired() {

        Clock fixedClock = Clock.fixed(
                Instant.now(),
                ZoneId.systemDefault()
        );

        jwtUtil = new JwtUtil(
                secret,
                1L,
                1L,
                fixedClock
        );

        String token = jwtUtil.generateToken(
                "test@gmail.com",
                "ADMIN"
        );

        Clock laterClock = Clock.offset(
                fixedClock,
                Duration.ofMillis(10)
        );

        JwtUtil expiredJwtUtil = new JwtUtil(
                secret,
                1L,
                1L,
                laterClock
        );

        UserDetails userDetails =
                User.builder()
                        .username("test@gmail.com")
                        .password("password")
                        .roles("ADMIN")
                        .build();

        boolean result =
                expiredJwtUtil.isTokenValid(token, userDetails);

        assertFalse(result);
    }

    @Test
    void shouldGenerateRefreshToken() {

        String refreshToken = jwtUtil.generateRefreshToken(
                "test@gmail.com",
                "ADMIN"
        );

        assertNotNull(refreshToken);
        assertFalse(refreshToken.isBlank());
    }

}
