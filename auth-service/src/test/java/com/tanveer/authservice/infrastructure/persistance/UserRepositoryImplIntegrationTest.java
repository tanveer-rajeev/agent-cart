package com.tanveer.authservice.infrastructure.persistance;

import com.tanveer.authservice.domain.User;
import com.tanveer.authservice.infrastructure.exception.CustomException;
import com.tanveer.authservice.infrastructure.exception.ResourceConflictException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@Testcontainers
@Import(UserRepositoryImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryImplIntegrationTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @BeforeEach
    void cleanDatabase() {
        userJpaRepository.deleteAll();
    }


    @Test
    void connectionEstablished() {
        Assertions.assertThat(postgres.isCreated()).isTrue();
        Assertions.assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void shouldSaveUser() {

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email("tanveer@gmail.com")
                .role("admin")
                .password("123")
                .build();

        User saved = userRepository.save(user);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(user.getId());
        assertThat(saved.getEmail()).isEqualTo("tanveer@gmail.com");

        Optional<UserEntity> entity =
                userJpaRepository.findByEmail("tanveer@gmail.com");

        assertThat(entity).isPresent();
        assertThat(entity.get().getEmail()).isEqualTo("tanveer@gmail.com");
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .email("tanveer@gmail.com")
                .role("admin")
                .password("123")
                .build();

        userJpaRepository.save(entity);

        User newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .email("tanveer@gmail.com")
                .role("admin")
                .password("123")
                .build();

        assertThatThrownBy(() -> userRepository.save(newUser))
                .isInstanceOf(ResourceConflictException.class)
                .hasMessage("Email already exist");
    }

    @Test
    void shouldFindUserByEmail() throws Exception {

        UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .email("tanveer@gmail.com")
                .role("admin")
                .password("123")
                .build();

        userJpaRepository.save(entity);

        User found = userRepository.findByEmail("tanveer@gmail.com");

        assertThat(found.getEmail()).isEqualTo("tanveer@gmail.com");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        assertThatThrownBy(() ->
                userRepository.findByEmail("unknown@gmail.com"))
                .isInstanceOf(CustomException.class)
                .hasMessage("user not found");
    }

    @Test
    void shouldUpdateUser() throws CustomException {

        UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .email("old@gmail.com")
                .role("admin")
                .password("123")
                .build();

        userJpaRepository.save(entity);

        User updated = User.builder()
                .id(entity.getId())
                .email("new@gmail.com")
                .role("admin")
                .password("234")
                .build();

        User result = userRepository.update(updated, entity.getId());

        assertThat(result.getEmail()).isEqualTo("new@gmail.com");
        assertThat(result.getPassword()).isEqualTo("234");
        assertThat(result.getRole()).isEqualTo("admin");

        UserEntity db = userJpaRepository.findById(entity.getId()).orElseThrow();

        assertThat(db.getEmail()).isEqualTo("new@gmail.com");
        assertThat(db.getPassword()).isEqualTo("234");
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithDuplicateEmail() {

        UserEntity first = UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .email("first@gmail.com")
                .role("admin")
                .password("123")
                .build();

        UserEntity second = UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .email("second@gmail.com")
                .role("admin")
                .password("123")
                .build();

        userJpaRepository.save(first);
        userJpaRepository.save(second);

        User update = User.builder()
                .id(second.getId())
                .email("first@gmail.com")
                .role("admin")
                .password("123")
                .build();

        assertThatThrownBy(() ->
                userRepository.update(update, second.getId()))
                .isInstanceOf(ResourceConflictException.class)
                .hasMessage("Email already exist");
    }
}