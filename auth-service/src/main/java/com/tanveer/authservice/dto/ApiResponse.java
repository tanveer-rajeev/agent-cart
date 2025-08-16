package com.tanveer.authservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
  private boolean success;
  private String message;
  private Object data;
}
