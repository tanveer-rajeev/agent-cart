package com.tanveer.authservice.infrastructure.persistance;

import com.tanveer.authservice.domain.User;
import com.tanveer.authservice.infrastructure.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Test
    void findByEmail() throws CustomException {
        UserEntity entity = new UserEntity();
        entity.setId("1");
        entity.setEmail("test@gmail.com");
        entity.setPassword("123");
        entity.setRole("ADMIN");

        when(userJpaRepository.findByEmail(entity.getEmail()))
                .thenReturn(Optional.of(entity));

        User result = userRepository.findByEmail("test@gmail.com");
        assertNotNull(result);
        assertEquals(entity.getEmail(),result.getEmail());
        verify(userJpaRepository).findByEmail(entity.getEmail());
    }

    @Test
    void should_throw_exception_when_user_not_found() {

        String email = "notfound@gmail.com";

        when(userJpaRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class,
                () -> userRepository.findByEmail(email)
        );

        assertEquals("user not found", ex.getMessage());
    }
}