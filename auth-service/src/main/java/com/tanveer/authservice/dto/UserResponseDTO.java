package com.tanveer.authservice.dto;

import com.tanveer.authservice.entity.UserEntity;

import java.util.UUID;

public record UserResponseDTO(
  UUID id,
  String email,
  String role
) {
  // Factory method to convert from User entity
  public static UserResponseDTO from(UserEntity userEntity) {
    return new UserResponseDTO(
      userEntity.getId(),
      userEntity.getEmail(),
      userEntity.getRole()
    );
  }
}

