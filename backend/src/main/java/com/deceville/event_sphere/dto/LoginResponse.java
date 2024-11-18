package com.deceville.event_sphere.dto;
import java.util.Objects;

public class LoginResponse {
  private String token;

  private long expiresIn;

  public LoginResponse() {}

  public LoginResponse(String token, long expiresIn) {
    this.token = token;
    this.expiresIn = expiresIn;
  }
  
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getExpiresIn() {
    return this.expiresIn;
  }

  public void setExpiresIn(long expiresIn) {
    this.expiresIn = expiresIn;
  }

  public LoginResponse token(String token) {
    setToken(token);
    return this;
  }

  public LoginResponse expiresIn(long expiresIn) {
    setExpiresIn(expiresIn);
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LoginResponse)) {
            return false;
        }
        LoginResponse loginResponse = (LoginResponse) o;
        return Objects.equals(token, loginResponse.token) && expiresIn == loginResponse.expiresIn;
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, expiresIn);
  }

  @Override
  public String toString() {
    return "{" +
      " token='" + getToken() + "'" +
      ", expiresIn='" + getExpiresIn() + "'" +
      "}";
  }
}
