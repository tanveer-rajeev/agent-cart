package com.tanveer.authservice.application;

import com.tanveer.authservice.domain.User;
import com.tanveer.authservice.domain.UserRepository;
import com.tanveer.authservice.infrastructure.dto.AuthResponse;
import com.tanveer.authservice.infrastructure.dto.LoginRequestDTO;
import com.tanveer.authservice.infrastructure.exception.CustomException;
import com.tanveer.authservice.infrastructure.persistance.TokenRepository;
import com.tanveer.authservice.infrastructure.service.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthUseCaseImpl authUseCase;

    @Mock
    private Authentication authentication;

    @Test
    void authenticate_validUser_returnsAuthResponse() throws CustomException {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("test@gmail.com");
        loginRequestDTO.setPassword("123");

        User user = User.create("1", "test@gmail.com", "password", "ADMIN");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(true);

        GrantedAuthority grantedAuthority = mock(GrantedAuthority.class);
        when(grantedAuthority.getAuthority()).thenReturn("ADMIN");
        when(authentication.getAuthorities())
                .thenAnswer(invocation -> Collections.singletonList(grantedAuthority));

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);
        when(jwtUtil.generateToken("test@gmail.com", "ADMIN")).thenReturn("ACCESS_TOKEN");
        when(jwtUtil.generateRefreshToken("test@gmail.com", "ADMIN")).thenReturn("REFRESH_TOKEN");

        AuthResponse authResponse = authUseCase.authenticate(loginRequestDTO);

        assertNotNull(authResponse);
        assertEquals("ACCESS_TOKEN", authResponse.getAccessToken());
        assertEquals("REFRESH_TOKEN", authResponse.getRefreshToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenRepository).revokeAllUserTokens("test@gmail.com");
        verify(tokenRepository).saveUserToken(user, "ACCESS_TOKEN");
    }

    @Test
    void authenticate_invalidUser_throwsException() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("test@gmail.com");
        loginRequestDTO.setPassword("123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(false);

        CustomException customException = assertThrows(CustomException.class,
                () -> authUseCase.authenticate(loginRequestDTO));

        assertEquals("email or password", customException.getMessage());

    }

    @Test
    void validateToken() throws CustomException {
        String token = "valid-token";

        authUseCase.validateToken(token);

        verify(jwtUtil).validateToken(token);
    }
}