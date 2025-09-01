package com.tanveer.authservice.infrastructure.mapper;

import com.tanveer.authservice.domain.User;
import com.tanveer.authservice.infrastructure.dto.AuthResponse;
import com.tanveer.authservice.infrastructure.dto.LoginRequestDTO;
import com.tanveer.authservice.infrastructure.dto.SignUpRequestDto;
import com.tanveer.authservice.infrastructure.persistance.UserEntity;

public class UserMapper {

    public static User entityToDomain(UserEntity userEntity) {
        return User.builder().id(userEntity.getId()).email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole()).build();
    }

    public static UserEntity domainToEntity(User user){
        return UserEntity.builder().id(user.getId()).email(user.getEmail())
                .password(user.getPassword()).role(user.getRole()).build();
    }

    public static User signUpDtoToDomain(SignUpRequestDto signUpRequestDto) {
        return User.builder().email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .role(signUpRequestDto.getRole()).build();
    }

    public static User loginDtoToDomain(LoginRequestDTO loginRequestDTO) {
        return User.builder().email(loginRequestDTO.getEmail())
                .password(loginRequestDTO.getPassword()).build();
    }
}
