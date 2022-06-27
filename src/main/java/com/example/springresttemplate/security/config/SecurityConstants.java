package com.example.springresttemplate.security.config;

import org.springframework.stereotype.Component;

import com.example.springresttemplate.config.AppCustomContext;
import com.example.springresttemplate.util.AppProperties;

@Component
public class SecurityConstants {
  public static final long EXPIRATION_DATE = 864000000;

  public static final String TOKEN_PREFIX = "Bearer ";

  public static final String HEADER_STRING = "Authorization";

  public static String getTokenSecret() {
    AppProperties appProperties = (AppProperties) AppCustomContext.getBean("AppProperties");

    return appProperties.getToKenSecret();
  }
}
