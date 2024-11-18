package com.deceville.event_sphere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all endpoints
            .allowedOrigins("http://localhost:4200") // Allow your frontend origin
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // HTTP methods
            .allowedHeaders("*") // Allow all headers
            .allowCredentials(true); // Allow cookies/auth headers
      }
    };
  }
}
