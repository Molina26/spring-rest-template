package com.example.springresttemplate.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAppService extends UserDetailsService{
  UserDetails loadUserByUsername(String username);
}
