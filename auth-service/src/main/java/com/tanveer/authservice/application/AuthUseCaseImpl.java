package com.tanveer.authservice.application;

import com.tanveer.authservice.infrastructure.persistance.TokenRepository;
import com.tanveer.authservice.domain.User;
import com.tanveer.authservice.domain.UserRepository;
import com.tanveer.authservice.infrastructure.dto.AuthResponse;
import com.tanveer.authservice.infrastructure.dto.LoginRequestDTO;
import com.tanveer.authservice.infrastructure.exception.CustomException;
import com.tanveer.authservice.infrastructure.mapper.UserMapper;
import com.tanveer.authservice.infrastructure.service.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse authenticate(LoginRequestDTO loginRequestDTO) throws CustomException {
        User user = UserMapper.loginDtoToDomain(loginRequestDTO);
        log.debug("Authenticating user by {} address", user.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            log.debug("Authenticated user by {} address", user.getEmail());

            String role = authentication.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse(null);

            User domain = userRepository.findByEmail(user.getEmail());

            log.debug("Revoking all token of the user by {} address", user.getEmail());

            tokenRepository.revokeAllUserTokens(user.getEmail());
            String token = jwtUtil.generateToken(user.getEmail(), role);
            tokenRepository.saveUserToken(domain, token);

            return AuthResponse.builder()
                    .accessToken(jwtUtil.generateToken(domain.getEmail(), role))
                    .refreshToken(jwtUtil.generateRefreshToken(domain.getEmail(), role))
                    .build();
        } else {
            log.error("Invalid email or password by {}", user.getEmail());
            throw new CustomException("email or password");
        }
    }

    @Override
    public void validateToken(String token) throws CustomException {
        log.info("Validated token");
        jwtUtil.validateToken(token);
    }
}
