package com.example.springresttemplate.services;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springresttemplate.entities.UserApp;
import com.example.springresttemplate.repositories.UserAppRepository;

@Service
public class UserAppServiceImpl implements UserAppService{

  private final UserAppRepository userAppRepository;

  public UserAppServiceImpl(UserAppRepository appRepository) {
    this.userAppRepository = appRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    
    UserApp userToAuthenticate = userAppRepository.findByUsername(username);

    if (userToAuthenticate == null) {
      throw new UsernameNotFoundException(username);
    }

    return new User(userToAuthenticate.getUsername(), userToAuthenticate.getPassword(), new ArrayList<>());
  }
  
}
