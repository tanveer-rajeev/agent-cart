package com.tanveer.authservice.domain;

import com.tanveer.authservice.infrastructure.exception.CustomException;
import com.tanveer.authservice.infrastructure.exception.ResourceConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserByEmail_existingEmail_returnsUser() throws CustomException {
        User user = new User("1", "test@gmail.com", "123", "ADMIN");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User userByEmail = userService.getUserByEmail(user.getEmail());

        assertNotNull(userByEmail);
        assertEquals(userByEmail.getEmail(), user.getEmail());
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void getUserByEmail_nonExistingEmail_returnsNull() throws CustomException {
        when(userRepository.findByEmail("abc@gmail.com")).thenReturn(null);

        User actual = userService.getUserByEmail("abc@gmail.com");

        assertNull(actual);
    }

    @Test
    void saveUser_validUser_saveAndReturnUser() {
        User expected = new User("1", "test@gmail.com", "123", "ADMIN");
        User actual = userService.saveUser(expected);

        assertNotNull(actual);
        assertEquals(expected, actual);

        verify(userRepository).save(expected);
    }

    @Test
    void saveUser_invalidUser_throwCustomException() throws CustomException {
        User expected = new User("1", "test@gmail.com", "123", "ADMIN");

        when(userRepository.save(expected)).thenThrow(new ResourceConflictException("DB error"));

        assertThrows(ResourceConflictException.class, () -> userService.saveUser(expected));

        verify(userRepository).save(expected);
    }
}