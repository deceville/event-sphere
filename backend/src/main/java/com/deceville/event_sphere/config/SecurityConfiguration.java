package com.deceville.event_sphere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  private final AuthenticationProvider authenticationProvider;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfiguration(
      JwtAuthenticationFilter jwtAuthenticationFilter,
      AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    try {
      http
          .csrf(csrf -> csrf.disable()) // Disable CSRF if using JWT
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/auth/**").permitAll() // Permit auth requests
              .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow OPTIONS requests globally
              .requestMatchers("/events/manage/**").hasAnyRole("ORGANIZER", "ADMIN")
              .requestMatchers("/events/reserve/**").hasAnyRole("AUDIENCE", "ADMIN")
              .requestMatchers("/events/view/**").permitAll()
              .requestMatchers("/reservations/**").hasAnyRole("AUDIENCE", "ADMIN")
              .anyRequest().authenticated() // Protect all other endpoints
          )
          .httpBasic(withDefaults()) // Basic authentication
          .exceptionHandling(exception -> exception
              .authenticationEntryPoint((request, response, authException) -> {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Error: Unauthorized. Please log in.");
              })
              .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter()
                    .write("Error: Forbidden. You do not have the required role to access this resource.");
              })) // Handle exception for unauthorized and forbidden access
          .sessionManagement(session -> session
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Disable session creation
          )
          .authenticationProvider(authenticationProvider)
          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(List.of("http://localhost:4200"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
