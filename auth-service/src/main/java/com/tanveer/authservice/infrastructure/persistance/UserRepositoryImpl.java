package com.tanveer.authservice.infrastructure.persistance;

import com.tanveer.authservice.domain.User;
import com.tanveer.authservice.domain.UserRepository;
import com.tanveer.authservice.infrastructure.exception.CustomException;
import com.tanveer.authservice.infrastructure.exception.ResourceConflictException;
import com.tanveer.authservice.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        log.info("Saving into database {}", user);
        if (userJpaRepository.findByEmail(user.getEmail()).isPresent()) {
            log.error("Email {} already exist", user.getEmail());
            throw new ResourceConflictException("Email already exist");
        }
        return UserMapper.entityToDomain(userJpaRepository.save(UserMapper.domainToEntity(user)));
    }

    @Override
    public User update(User user, String id) {

        Optional<UserEntity> existingUser =
                userJpaRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()
                && !existingUser.get().getId().equals(id)) {
            throw new ResourceConflictException("Email already exist");
        }

        return UserMapper.entityToDomain(
                userJpaRepository.saveAndFlush(
                        UserMapper.domainToEntity(user)
                )
        );
    }

    @Override
    public User findByEmail(String email) throws CustomException {
        return UserMapper.entityToDomain(userJpaRepository.findByEmail(email).stream().findFirst()
                .orElseThrow(() -> new CustomException("user not found")));
    }
}
