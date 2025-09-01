package com.tanveer.authservice.application;

import com.tanveer.authservice.domain.User;
import com.tanveer.authservice.domain.UserService;
import com.tanveer.authservice.infrastructure.dto.SignUpRequestDto;
import com.tanveer.authservice.infrastructure.dto.UserResponseDTO;
import com.tanveer.authservice.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO saveUser(SignUpRequestDto signUpRequestDto) throws CustomException {
        User domain = User.builder().id(UUID.randomUUID().toString()).email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .role(signUpRequestDto.getRole())
                .build();
        return UserResponseDTO.from(service.saveUser(domain));
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) throws CustomException {
        return UserResponseDTO.from(service.getUserByEmail(email));
    }
}
