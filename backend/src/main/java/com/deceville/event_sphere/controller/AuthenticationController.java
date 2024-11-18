package com.deceville.event_sphere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deceville.event_sphere.dto.LoginResponse;
import com.deceville.event_sphere.dto.UserLoginDTO;
import com.deceville.event_sphere.dto.UserRegistrationDTO;
import com.deceville.event_sphere.entity.User;
import com.deceville.event_sphere.service.AuthenticationService;
import com.deceville.event_sphere.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private JwtService jwtService; // Utility for generating tokens

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody UserRegistrationDTO userRegistrationDto) {
    User registeredUser = authenticationService.register(userRegistrationDto);

    return ResponseEntity.ok(registeredUser);
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticate(@RequestBody UserLoginDTO userLoginDto) {
    try {
      User authenticatedUser = authenticationService.authenticate(userLoginDto);

      String jwtToken = jwtService.generateToken(authenticatedUser.getId(), authenticatedUser.getRole(),
          authenticatedUser);

      LoginResponse loginResponse = new LoginResponse();
      loginResponse.setToken(jwtToken);
      loginResponse.setExpiresIn(jwtService.getExpirationTime());

      return ResponseEntity.ok(loginResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
  }
}
