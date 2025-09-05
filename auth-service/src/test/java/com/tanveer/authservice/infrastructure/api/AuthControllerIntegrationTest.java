package com.tanveer.authservice.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanveer.authservice.infrastructure.dto.SignUpRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    private static final String BASE_URL = "/api/v1/auth";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_ValidRequest_Returns201() throws Exception {
        var request = new SignUpRequestDto("test@gmail.com", "123", "ADMIN");

        mockMvc.perform(post(BASE_URL+"/signup")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void createUser_DuplicateEmail_Returns409() throws Exception {
        var request = new SignUpRequestDto("test@gmail.com", "123", "ADMIN");

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email already exist"));
    }

    @Test
    void createUser_MissingFields_Returns400() throws Exception {
        var request = new SignUpRequestDto("", "123", "");

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(3));
    }

}