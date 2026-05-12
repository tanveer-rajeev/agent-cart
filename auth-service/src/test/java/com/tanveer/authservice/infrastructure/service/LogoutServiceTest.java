package com.tanveer.authservice.infrastructure.service;

import com.tanveer.authservice.infrastructure.persistance.TokenEntity;
import com.tanveer.authservice.infrastructure.persistance.TokenJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LogoutServiceTest {

    @Mock
    private TokenJpaRepository tokenJpaRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LogoutService logoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldDoNothingWhenNoAuthHeader() {

        when(request.getHeader("Authorization")).thenReturn(null);

        logoutService.logout(request, response, authentication);

        verify(tokenJpaRepository, never()).save(any());
    }

    @Test
    void shouldDoNothingWhenHeaderNotBearer() {

        when(request.getHeader("Authorization")).thenReturn("Basic xyz");

        logoutService.logout(request, response, authentication);

        verify(tokenJpaRepository, never()).save(any());
    }

    @Test
    void shouldDoNothingWhenTokenNotFound() {

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer token123");

        when(tokenJpaRepository.findByToken("token123"))
                .thenReturn(Optional.empty());

        logoutService.logout(request, response, authentication);

        verify(tokenJpaRepository, never()).save(any());
    }

    @Test
    void shouldRevokeAndExpireTokenSuccessfully() {

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer token123");

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken("token123");
        tokenEntity.setExpired(false);
        tokenEntity.setRevoked(false);

        when(tokenJpaRepository.findByToken("token123"))
                .thenReturn(Optional.of(tokenEntity));

        logoutService.logout(request, response, authentication);

        assertTrue(tokenEntity.isExpired());
        assertTrue(tokenEntity.isRevoked());

        verify(tokenJpaRepository, times(1)).save(tokenEntity);
    }

    @Test
    void shouldClearSecurityContextWhenLogout() {

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer token123");

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken("token123");

        when(tokenJpaRepository.findByToken("token123"))
                .thenReturn(Optional.of(tokenEntity));

        logoutService.logout(request, response, authentication);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}