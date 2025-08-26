package com.tanveer.authservice.service;

import com.tanveer.authservice.domain.UserEntity;
import com.tanveer.authservice.dto.AuthResponse;
import com.tanveer.authservice.dto.LoginRequestDTO;
import com.tanveer.authservice.dto.UserResponseDTO;
import com.tanveer.authservice.exception.CustomException;

import java.util.UUID;

public interface AuthService {
  UserResponseDTO getUserByEmail(String email) throws CustomException;
  UserResponseDTO getUserById(String id) throws CustomException;
  UserResponseDTO saveUser(UserEntity userEntity) throws CustomException;
  AuthResponse authenticate(LoginRequestDTO loginRequestDTO) throws CustomException;
  void validateToken(String token) throws CustomException;
}
