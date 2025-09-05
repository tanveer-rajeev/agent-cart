package com.tanveer.authservice.infrastructure.api;

import com.tanveer.authservice.application.AuthUseCase;
import com.tanveer.authservice.application.UserUseCase;
import com.tanveer.authservice.infrastructure.dto.AuthResponse;
import com.tanveer.authservice.infrastructure.dto.LoginRequestDTO;
import com.tanveer.authservice.infrastructure.dto.SignUpRequestDto;
import com.tanveer.authservice.infrastructure.dto.UserResponseDTO;
import com.tanveer.authservice.infrastructure.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AuthControllerUnitTest {

    @Mock
    private UserUseCase userUseCase;

    @Mock
    private AuthUseCase authUseCase;

    @InjectMocks
    private AuthController authController;

    @Test
    void testSaveUser_ReturnsCreated() throws CustomException {
        SignUpRequestDto dto = new SignUpRequestDto("test@gmail.com", "123", "ADMIN");
        UserResponseDTO response = new UserResponseDTO("test@gmail.com", "ADMIN");

        when(userUseCase.saveUser(any())).thenReturn(response);

        ResponseEntity<UserResponseDTO> result = authController.saveUser(dto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        Assertions.assertNotNull(result.getBody());
        assertEquals("test@gmail.com", result.getBody().email());
        assertEquals("ADMIN", result.getBody().role());
    }

    @Test
    void getUserIdByEmail() throws CustomException {
        String email = "test@gmail.com";
        UserResponseDTO response = new UserResponseDTO("test@gmail.com","ADMIN");

        when(userUseCase.getUserByEmail(any())).thenReturn(response);

        ResponseEntity<UserResponseDTO> actual = authController.getUserIdByEmail(email);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertNotNull(actual.getBody());
        assertEquals("test@gmail.com", actual.getBody().email());
        assertEquals("ADMIN", actual.getBody().role());
    }

    @Test
    void login() throws CustomException {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("test@gmail.com");
        loginRequestDTO.setPassword("123");

        AuthResponse response = AuthResponse.builder().accessToken("ACCESS_TOKEN").refreshToken("REFRESH_TOKEN")
                        .build();

        when(authUseCase.authenticate(loginRequestDTO)).thenReturn(response);

        ResponseEntity<AuthResponse> actual = authController.login(loginRequestDTO);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK,actual.getStatusCode());
        assertEquals(response,actual.getBody());

        verify(authUseCase).authenticate(loginRequestDTO);
    }

    @Test
    void validateToken() throws CustomException {
        String token = "valid-token";

        ResponseEntity<HttpStatus> actual = authController.validateToken(token);

        assertEquals(HttpStatus.ACCEPTED,actual.getStatusCode());

        verify(authUseCase).validateToken(token);
    }
}