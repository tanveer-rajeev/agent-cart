package com.tanveer.authservice.repository;

import com.tanveer.authservice.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findById(UUID id);

}
