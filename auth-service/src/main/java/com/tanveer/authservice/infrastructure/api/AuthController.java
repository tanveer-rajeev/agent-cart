package com.tanveer.authservice.infrastructure.api;

import com.tanveer.authservice.application.AuthUseCase;
import com.tanveer.authservice.application.UserUseCase;
import com.tanveer.authservice.infrastructure.dto.AuthResponse;
import com.tanveer.authservice.infrastructure.dto.LoginRequestDTO;
import com.tanveer.authservice.infrastructure.dto.SignUpRequestDto;
import com.tanveer.authservice.infrastructure.dto.UserResponseDTO;
import com.tanveer.authservice.infrastructure.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserUseCase userUseCase;
    private final AuthUseCase authUseCase;

    @Operation(summary = "Signup for new user")
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto) throws CustomException {
        return ResponseEntity.status(201).body(userUseCase.saveUser(signUpRequestDto));
    }

    @Operation(summary = "Get user by email")
    @GetMapping("/getUser/{email}")
    public ResponseEntity<UserResponseDTO> getUserIdByEmail(@PathVariable String email) throws CustomException {
        return ResponseEntity.ok().body(userUseCase.getUserByEmail(email));
    }

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) throws CustomException {
        return ResponseEntity.ok(authUseCase.authenticate(loginRequestDTO));
    }

    @Operation(summary = "Validate jwt token")
    @GetMapping("/validate")
    public ResponseEntity<HttpStatus> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws CustomException {
        authUseCase.validateToken(token);
        return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
    }
}
