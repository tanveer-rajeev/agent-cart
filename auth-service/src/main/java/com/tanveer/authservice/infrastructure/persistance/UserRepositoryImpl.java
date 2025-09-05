package com.tanveer.authservice.infrastructure.persistance;

import com.tanveer.authservice.domain.User;
import com.tanveer.authservice.domain.UserRepository;
import com.tanveer.authservice.infrastructure.exception.CustomException;
import com.tanveer.authservice.infrastructure.exception.ResourceConflictException;
import com.tanveer.authservice.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        log.info("Saving into database {}", user);
        isUserExist(user.getEmail());
        return UserMapper.entityToDomain(userJpaRepository.save(UserMapper.domainToEntity(user)));
    }

    @Override
    public User update(User user, String id) {
        isUserExist(user.getEmail());
        return UserMapper.entityToDomain(userJpaRepository.saveAndFlush(UserMapper.domainToEntity(user)));
    }

    @Override
    public User findByEmail(String email) throws CustomException {
        return UserMapper.entityToDomain(userJpaRepository.findByEmail(email).stream().findFirst()
                .orElseThrow(() -> new CustomException("user not found")));
    }

    private void isUserExist(String email) {
        if (userJpaRepository.findByEmail(email).isPresent()) {
            log.error("Email {} already exist", email);
            throw new ResourceConflictException("Email already exist");
        }
    }
}
