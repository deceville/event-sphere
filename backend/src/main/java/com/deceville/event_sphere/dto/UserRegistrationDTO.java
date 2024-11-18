package com.deceville.event_sphere.dto;
import java.util.Objects;

import com.deceville.event_sphere.entity.Role;

public class UserRegistrationDTO {
  private String username;
  private String password;
  private String firstname;
  private String lastname;
  private Role role;


  public UserRegistrationDTO() {}

  public UserRegistrationDTO(String username, String password, String firstname, String lastname, Role role) {
    this.username = username;
    this.password = password;
    this.firstname = firstname;
    this.lastname = lastname;
    this.role = role;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public Role getRole() {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public UserRegistrationDTO username(String username) {
    setUsername(username);
    return this;
  }

  public UserRegistrationDTO password(String password) {
    setPassword(password);
    return this;
  }

  public UserRegistrationDTO firstname(String firstname) {
    setFirstname(firstname);
    return this;
  }

  public UserRegistrationDTO lastname(String lastname) {
    setLastname(lastname);
    return this;
  }

  public UserRegistrationDTO role(Role role) {
    setRole(role);
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserRegistrationDTO)) {
            return false;
        }
        UserRegistrationDTO userRegistrationDTO = (UserRegistrationDTO) o;
        return Objects.equals(username, userRegistrationDTO.username) && Objects.equals(password, userRegistrationDTO.password) && Objects.equals(firstname, userRegistrationDTO.firstname) && Objects.equals(lastname, userRegistrationDTO.lastname) && Objects.equals(role, userRegistrationDTO.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, firstname, lastname, role);
  }

  @Override
  public String toString() {
    return "{" +
      " username='" + getUsername() + "'" +
      ", password='" + getPassword() + "'" +
      ", firstname='" + getFirstname() + "'" +
      ", lastname='" + getLastname() + "'" +
      ", role='" + getRole() + "'" +
      "}";
  }
}
