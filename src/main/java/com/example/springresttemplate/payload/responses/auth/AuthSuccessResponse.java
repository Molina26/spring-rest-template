package com.example.springresttemplate.payload.responses.auth;

import lombok.Data;

@Data
public class AuthSuccessResponse {
  private String message;
  private String token;
}
