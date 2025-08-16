package com.tanveer.authservice.serviceImpl;

import com.tanveer.authservice.dto.AuthResponse;
import com.tanveer.authservice.dto.LoginRequestDTO;
import com.tanveer.authservice.dto.UserResponseDTO;
import com.tanveer.authservice.domain.TokenEntity;
import com.tanveer.authservice.domain.TokenType;
import com.tanveer.authservice.domain.UserEntity;
import com.tanveer.authservice.exception.CustomException;
import com.tanveer.authservice.repository.TokenRepository;
import com.tanveer.authservice.repository.UserRepository;
import com.tanveer.authservice.service.AuthService;
import com.tanveer.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public UserResponseDTO getUserByEmail(String email) throws CustomException {

    log.info("Fetching user by {} email address", email);
    UserEntity user = userRepository.findByEmail(email).stream().findFirst()
      .orElseThrow(() -> new CustomException("Email not found"));
    return new UserResponseDTO(user.getId(), user.getEmail(), user.getRole());
  }

  public UserResponseDTO getUserById(UUID id) throws CustomException {

    log.info("Fetching user by {} id", id);

    UserEntity user = userRepository.findById(id).stream().findFirst()
      .orElseThrow(() -> new CustomException("User id not found"));
    return new UserResponseDTO(id, user.getEmail(), user.getRole());
  }

  public UserResponseDTO saveUser(UserEntity userEntity) throws CustomException {
    log.info("Saving user by {} email address", userEntity.getEmail());

    if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
      log.error("Email {} already exist", userEntity.getEmail());
      throw new CustomException("User name already exist");
    }

    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

    log.info("Saved user by {} mail address", userEntity.getEmail());

    userRepository.save(userEntity);
    return new UserResponseDTO(userEntity.getId(), userEntity.getEmail(),userEntity.getRole());
  }

  public AuthResponse authenticate(LoginRequestDTO loginRequestDTO) throws CustomException {
    log.debug("Authenticating user by {} address", loginRequestDTO.getEmail());

    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

    if (authentication.isAuthenticated()) {
      log.debug("Authenticated user by {} address", loginRequestDTO.getEmail());

      String role = authentication.getAuthorities()
        .stream()
        .findFirst()
        .map(GrantedAuthority::getAuthority)
        .orElse(null);

      UserEntity userEntity = userRepository.findByEmail(loginRequestDTO.getEmail())
        .stream().findFirst().orElseThrow(() -> new CustomException("User not found"));

      log.debug("Revoking all token of the user by {} address", loginRequestDTO.getEmail());

      revokeAllUserTokens(loginRequestDTO.getEmail());
      String token = jwtService.generateToken(loginRequestDTO.getEmail(), role);
      saveUserToken(userEntity, token);

      return AuthResponse.builder()
        .accessToken(jwtService.generateToken(loginRequestDTO.getEmail(), role))
        .refreshToken(jwtService.generateRefreshToken(loginRequestDTO.getEmail(), role))
        .build();
    } else {
      log.error("Invalid email or password by {}", loginRequestDTO.getEmail());
      throw new CustomException("email or password");
    }
  }

  public void validateToken(String token) throws CustomException {
    jwtService.validateToken(token);
  }

  private void revokeAllUserTokens(String email) {
    log.debug("Revoking all tokens for the email: {}", email);

    var validUserTokens = tokenRepository.findAllValidTokenByUser(email);

    if (validUserTokens.isEmpty()) {
      log.debug("No valid tokens to revoke for the email: {}", email);
      return;
    }

    validUserTokens.forEach(tokenEntity -> {
      tokenEntity.setExpired(true);
      tokenEntity.setRevoked(true);
    });

    tokenRepository.saveAll(validUserTokens);

    log.debug("Revoked all tokens for the email: {}", email);
  }


  private void saveUserToken(UserEntity userEntity, String jwtToken) {
    log.debug("OrganizerService:checkValidExpoId saving token for organizer id: {}", userEntity.getId());

    var token = TokenEntity.builder()
      .userEntity(userEntity)
      .token(jwtToken)
      .tokenType(TokenType.BEARER)
      .expired(false)
      .revoked(false)
      .build();
    tokenRepository.save(token);

    log.debug("OrganizerService:checkValidExpoId token saved for organizer id: {}", userEntity.getId());
  }
}
