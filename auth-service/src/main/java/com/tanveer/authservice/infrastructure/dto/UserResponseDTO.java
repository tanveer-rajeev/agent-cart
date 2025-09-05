package com.tanveer.authservice.infrastructure.dto;

import com.tanveer.authservice.domain.User;

public record UserResponseDTO(
  String email,
  String role
) {
  // Factory method to convert from User entity
  public static UserResponseDTO from(User user) {
    return new UserResponseDTO(
            user.getEmail(),
            user.getRole()
    );
  }
}

