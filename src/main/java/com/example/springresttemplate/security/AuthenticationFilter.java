package com.example.springresttemplate.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springresttemplate.payload.requests.auth.UserLoginDto;
import com.example.springresttemplate.payload.responses.auth.AuthSuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public AuthenticationFilter(AuthenticationManager auth) {
    this.authenticationManager = auth;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    try {
      UserLoginDto userModel = new ObjectMapper().readValue(request.getInputStream(),
          UserLoginDto.class);

      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getUsername(),
          userModel.getPassword(), new ArrayList<>()));

    } catch (IOException exception) {

      throw new RuntimeException(exception);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {

    AuthSuccessResponse authResponse = new AuthSuccessResponse();

    String username = ((User) authResult.getPrincipal()).getUsername();

    // // Generate token
    String token = Jwts.builder().setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();

    authResponse.setToken(token);
    authResponse.setMessage("You are authenticate success");

    ObjectMapper map = new ObjectMapper();
    map.configure(SerializationFeature.INDENT_OUTPUT, true);

    String data = map.writeValueAsString(authResponse);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().print(data);
    response.flushBuffer();
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException failed) throws IOException, ServletException {
    Map<String, String> message = new HashMap<>();
    ObjectMapper map = new ObjectMapper();

    message.put("message", "The values are  incorrect");
    message.put("status", "error");

    map.configure(SerializationFeature.INDENT_OUTPUT, true);

    String data = map.writeValueAsString(message);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().print(data);
    response.flushBuffer();
  }

}
