package com.tanveer.authservice.application;

import com.tanveer.authservice.infrastructure.dto.AuthResponse;
import com.tanveer.authservice.infrastructure.dto.LoginRequestDTO;
import com.tanveer.authservice.infrastructure.dto.SignUpRequestDto;
import com.tanveer.authservice.infrastructure.dto.UserResponseDTO;
import com.tanveer.authservice.infrastructure.exception.CustomException;

public interface UserUseCase {
    UserResponseDTO getUserByEmail(String email) throws CustomException;
    UserResponseDTO saveUser(SignUpRequestDto signUpRequestDto) throws CustomException;
}
