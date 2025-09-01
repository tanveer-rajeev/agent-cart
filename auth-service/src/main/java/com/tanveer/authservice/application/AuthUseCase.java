package com.tanveer.authservice.application;

import com.tanveer.authservice.infrastructure.dto.AuthResponse;
import com.tanveer.authservice.infrastructure.dto.LoginRequestDTO;
import com.tanveer.authservice.infrastructure.exception.CustomException;

public interface AuthUseCase {
    AuthResponse authenticate(LoginRequestDTO loginRequestDTO) throws CustomException;
    void validateToken(String token) throws CustomException;
}
