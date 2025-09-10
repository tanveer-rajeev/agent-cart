package com.tanveer.authservice.infrastructure.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
  private boolean success;
  private String message;
  private Object data;
  private List<String> errors;
}
