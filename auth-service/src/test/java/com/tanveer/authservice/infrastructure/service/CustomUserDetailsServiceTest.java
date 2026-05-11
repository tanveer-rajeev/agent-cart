package com.tanveer.authservice.infrastructure.service;

import com.tanveer.authservice.infrastructure.persistance.UserEntity;
import com.tanveer.authservice.infrastructure.persistance.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldLoadUserByUsername() {

        UserEntity user = new UserEntity();
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setRole("ADMIN");

        when(userJpaRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        UserDetails result =
                customUserDetailsService.loadUserByUsername("test@gmail.com");

        assertEquals("test@gmail.com", result.getUsername());
        assertEquals("password", result.getPassword());

        assertTrue(
                result.getAuthorities()
                        .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userJpaRepository.findByEmail("abc@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("abc@gmail.com")
        );
    }
}
