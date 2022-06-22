package com.example.springresttemplate.payload.acount.request;

import javax.validation.constraints.Min;

import com.example.springresttemplate.constraints.annotations.UniqueUsername;

import lombok.Data;

@Data
public class UserSingupRequestDto {
  
  @UniqueUsername
  private String username;

  @Min(value = 5)
  private String password;
  
}
