package com.tanveer.authservice.controller;

import com.tanveer.authservice.dto.AuthResponse;
import com.tanveer.authservice.dto.LoginRequestDTO;
import com.tanveer.authservice.dto.UserResponseDTO;
import com.tanveer.authservice.domain.UserEntity;
import com.tanveer.authservice.exception.CustomException;
import com.tanveer.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "Get user by email")
  @GetMapping("/getUser/{email}")
  public ResponseEntity<UserResponseDTO> getUserIdByEmail(@PathVariable String email) throws CustomException {
    return ResponseEntity.ok().body(authService.getUserByEmail(email));
  }

  @Operation(summary = "Get user by id")
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) throws CustomException {
    return ResponseEntity.ok().body(authService.getUserById(id));
  }

  @Operation(summary = "Signup for new user")
  @PostMapping("/signup")
  public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody UserEntity userEntity) throws CustomException {
    return ResponseEntity.status(200).body(authService.saveUser(userEntity));
  }

  @Operation(summary = "Generate token on user login")
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDTO loginRequestDTO) throws CustomException {
    log.debug("enter login method");

    return ResponseEntity.ok(authService.authenticate(loginRequestDTO));
  }

  @GetMapping("/validate")
  public String validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws CustomException {
    authService.validateToken(token);
    return "Token is valid";
  }
}
