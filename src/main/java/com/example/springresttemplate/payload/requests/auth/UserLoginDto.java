package com.example.springresttemplate.payload.requests.auth;

import lombok.Data;

@Data
public class UserLoginDto {
  private String username;
  private String password;
}
