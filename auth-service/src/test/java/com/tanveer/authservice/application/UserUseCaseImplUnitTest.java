package com.tanveer.authservice.application;

import com.tanveer.authservice.domain.User;
import com.tanveer.authservice.domain.UserService;
import com.tanveer.authservice.infrastructure.dto.SignUpRequestDto;
import com.tanveer.authservice.infrastructure.dto.UserResponseDTO;
import com.tanveer.authservice.infrastructure.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseImplUnitTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserUseCaseImpl userUseCase;

    @Test
    void saveUser_validRequest_returnsUserResponseDTO() throws CustomException {
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder().email("test@gmail.com")
                .password("rawPass").role("ADMIN").build();
        when(passwordEncoder.encode("rawPass")).thenReturn("encodedPass");

        User expected = User.builder().id("123").email("test@gmail.com")
                .password("encodedPass")
                .role("ADMIN").build();
        when(userService.saveUser(any(User.class))).thenReturn(expected);

        UserResponseDTO responseDTO = userUseCase.saveUser(signUpRequestDto);

        assertNotNull(responseDTO);
        assertEquals("test@gmail.com", responseDTO.email());
        assertEquals("ADMIN", responseDTO.role());

        verify(passwordEncoder).encode("rawPass");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveUser(captor.capture());
        User captured = captor.getValue();

        assertEquals("test@gmail.com", captured.getEmail());
        assertEquals("encodedPass", captured.getPassword()); // ensure encoded
        assertEquals("ADMIN", captured.getRole());
    }

    @Test
    void saveUser_whenUserServiceThrows_propagatesException() throws CustomException {
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder().email("test@gmail.com")
                .password("rawPass").role("ADMIN").build();
        when(userService.saveUser(any(User.class))).thenThrow(new CustomException("DB error"));

        CustomException customException = assertThrows(CustomException.class,
                () -> userUseCase.saveUser(signUpRequestDto));

        assertEquals("DB error", customException.getMessage());
    }

    @Test
    void getUserByEmail_getUser_returnsUserResponseDTO() throws CustomException {
        User expected = User.builder().id("123").email("test@gmail.com")
                .password("encodedPass")
                .role("ADMIN").build();
        when(userService.getUserByEmail(any(String.class))).thenReturn(expected);

        UserResponseDTO userByEmail = userUseCase.getUserByEmail("test@gmail.com");

        assertNotNull(userByEmail);
        assertEquals(expected.getEmail(), userByEmail.email());
        assertEquals(expected.getRole(), userByEmail.role());
    }

    @Test
    void getUserByEmail_whenGetUserMethodThrows_propagatesExceptions() throws CustomException {
        when(userService.getUserByEmail(any(String.class))).thenThrow(new CustomException("DB error"));

        CustomException customException = assertThrows(CustomException.class,
                () -> userUseCase.getUserByEmail("test@gmail.com"));

        assertEquals("DB error",customException.getMessage());
    }
}