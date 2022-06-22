package com.example.springresttemplate.services;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springresttemplate.entities.UserApp;
import com.example.springresttemplate.payload.acount.request.UserSingupRequestDto;
import com.example.springresttemplate.repositories.UserAppRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAppServiceImpl implements UserAppService{

  private final Logger logger = LoggerFactory.getLogger(UserAppServiceImpl.class);

  private final UserAppRepository userAppRepository;

  private final BCryptPasswordEncoder encoder;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) {
    
    Optional<UserApp> user = userAppRepository.findByUsername(username);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException(username);
    }

    UserApp userToAuthenticate = user.get(); 

    return new User(userToAuthenticate.getUsername(), userToAuthenticate.getPassword(), new ArrayList<>());
  }

  @Override
  public UserApp createUser(UserSingupRequestDto userDto) {
    
    // if (userRepository.existsByUsername(signUpRequest.getUsername())) {
    //   return ResponseEntity
    //       .badRequest()
    //       .body(new MessageResponse("Error: Username is already taken!"));
    // }

    // if (userRepository.existsByEmail(signUpRequest.getEmail())) {
    //   return ResponseEntity
    //       .badRequest()
    //       .body(new MessageResponse("Error: Email is already in use!"));
    // }

    // Create new user's account
    // UserApp user = new User(signUpRequest.getUsername(), 
              //  signUpRequest.getEmail(),
              //  encoder.encode(signUpRequest.getPassword()));

    // Set<String> strRoles = signUpRequest.getRole();
    // Set<Role> roles = new HashSet<>();

    // if (strRoles == null) {
    //   Role userRole = roleRepository.findByName(ERole.ROLE_USER)
    //       .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //   roles.add(userRole);
    // } else {
    //   // Define the roles according your uses cases 
    //   strRoles.forEach(role -> {
    //     switch (role) {
    //     case "super":
    //       Role adminRole = roleRepository.findByName(ERole.ROLE_SUPER)
    //           .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //       roles.add(adminRole);

    //       break;
    //     case "user":
    //       Role modRole = roleRepository.findByName(ERole.ROLE_USER)
    //           .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //       roles.add(modRole);
    //       break;
    //     default:
    //       Role userRole = roleRepository.findByName(ERole.ROLE_USER)
    //           .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    //       roles.add(userRole);
    //     }
    //   });
    // }

    // user.setRoles(roles);
    // userRepository.save(user);  

    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public UserApp findUserByUsername(String username) {
    try {
      Optional<UserApp> user = userAppRepository.findByUsername(username);
    
      if (user.isPresent()) {
        return user.get();
      }      
    } catch (Exception e) {
      logger.error("error to find user by username "+ e.getMessage());
    }
    
    return null;
  }
}
