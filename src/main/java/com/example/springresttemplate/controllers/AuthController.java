package com.example.springresttemplate.controllers;

import java.beans.Encoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springresttemplate.entities.Role;
import com.example.springresttemplate.entities.UserApp;
import com.example.springresttemplate.exceptions.AppCustomException;
import com.example.springresttemplate.payload.acount.request.UserSinginRequestDto;
import com.example.springresttemplate.payload.acount.request.UserSingupRequestDto;
import com.example.springresttemplate.payload.acount.response.SinginSuccessResponseDto;
import com.example.springresttemplate.repositories.RoleRepository;
import com.example.springresttemplate.security.config.SecurityConstants;
import com.example.springresttemplate.security.jwt.JwtUtils;
import com.example.springresttemplate.services.UserAppServiceImpl;
import com.example.springresttemplate.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(SecurityConstants.LOGIN_URL)
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  private UserAppServiceImpl userAppServiceImpl;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private BCryptPasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  private final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserSinginRequestDto loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserApp userDetails = userAppServiceImpl.findUserByUsername(authentication.getName());

    if (!userDetails.isIsAvailable()) {
      logger.error("user isn't available");
      throw new AppCustomException("authentication","El usuario esta inhabilitado");
    }

    if (userDetails.getRoles().isEmpty()) {
      logger.error("user haven´t roles");
      throw new AppCustomException("authentication","El usuario no tiene un rol asignado");
    }

    List<String> roles = userDetails.getRoles().stream().map(item -> item.getName()).collect(Collectors.toList());

    return ResponseEntity.ok(new SinginSuccessResponseDto(jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserSingupRequestDto signUpRequest) {
  // if (userRepository.existsByUsername(signUpRequest.getUsername())) {
  // return ResponseEntity
  // .badRequest()
  // .body(new MessageResponse("Error: Username is already taken!"));
  // }

  // if (userRepository.existsByEmail(signUpRequest.getEmail())) {
  // return ResponseEntity
  // .badRequest()
  // .body(new MessageResponse("Error: Email is already in use!"));
  // }

  // // Create new user's account
  // // UserApp user = new User(signUpRequest.getUsername(),
  // // signUpRequest.getEmail(),
  // // encoder.encode(signUpRequest.getPassword()));

  // Set<String> strRoles = signUpRequest.getRole();
  // Set<Role> roles = new HashSet<>();

  // if (strRoles == null) {
  // Role userRole = roleRepository.findByName(ERole.ROLE_USER)
  // .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
  // roles.add(userRole);
  // } else {
  // // Define the roles according your uses cases
  // strRoles.forEach(role -> {
  // switch (role) {
  // case "super":
  // Role adminRole = roleRepository.findByName(ERole.ROLE_SUPER)
  // .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
  // roles.add(adminRole);

  // break;
  // case "user":
  // Role modRole = roleRepository.findByName(ERole.ROLE_USER)
  // .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
  // roles.add(modRole);
  // break;
  // default:
  // Role userRole = roleRepository.findByName(ERole.ROLE_USER)
  // .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
  // roles.add(userRole);
  // }
  // });
  // }

  // user.setRoles(roles);
  // userRepository.save(user);

  return ResponseEntity.ok("User registered successfully!");
  }
}
