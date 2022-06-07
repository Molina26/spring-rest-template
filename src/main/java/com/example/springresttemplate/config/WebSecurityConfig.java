package com.example.springresttemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.springresttemplate.services.UserAppServiceImpl;

@Configuration
public class WebSecurityConfig {

  private final UserAppServiceImpl appServiceImpl;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public WebSecurityConfig(UserAppServiceImpl userAppServiceImpl, BCryptPasswordEncoder encoder) {
    this.appServiceImpl = userAppServiceImpl;
    this.bCryptPasswordEncoder = encoder;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable();
    http
        .authorizeHttpRequests((authz) -> authz
            .antMatchers("/test").permitAll().anyRequest().authenticated());

    // http.addFilterBefore(filter, beforeFilter)
    return http.build();
  }

  // @Bean
  // public WebSecurityCustomizer webSecurityCustomizer() {
  // // Define the paths to access
  // return (web) -> web.ignoring().antMatchers("/test");
  // }
}
