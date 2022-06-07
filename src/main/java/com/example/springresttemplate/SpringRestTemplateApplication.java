package com.example.springresttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.springresttemplate.config.AppCustomContext;
import com.example.springresttemplate.util.AppProperties;

@SpringBootApplication
public class SpringRestTemplateApplication {

	@Bean
	public static BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean(name = "AppProperties")
	public static AppProperties getAppProperties() {
		return new AppProperties();
	}

	@Bean
	public static AppCustomContext appCustomContext() {
		return new AppCustomContext();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringRestTemplateApplication.class, args);
	}

}
