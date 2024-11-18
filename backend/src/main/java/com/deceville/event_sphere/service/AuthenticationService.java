package com.deceville.event_sphere.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deceville.event_sphere.dto.UserLoginDTO;
import com.deceville.event_sphere.dto.UserRegistrationDTO;
import com.deceville.event_sphere.entity.User;
import com.deceville.event_sphere.repository.UserRepository;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User register(UserRegistrationDTO input) {
    User user = new User();
    user.setFirstname(input.getFirstname());
    user.setLastname(input.getLastname());
    user.setUsername(input.getUsername());
    user.setRole(input.getRole());
    user.setPassword(passwordEncoder.encode(input.getPassword()));
    ;

    return userRepository.save(user);
  }

  public User authenticate(UserLoginDTO input) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            input.getUsername(),
            input.getPassword()));

    return userRepository.findByUsername(input.getUsername())
        .orElseThrow();
  }
}
