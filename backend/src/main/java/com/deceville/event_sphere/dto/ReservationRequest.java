package com.deceville.event_sphere.dto;

import java.util.Objects;

public class ReservationRequest {
  private Long userId;

  public ReservationRequest() {
  }

  public ReservationRequest(Long userId) {
    this.userId = userId;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public ReservationRequest userId(Long userId) {
    setUserId(userId);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof ReservationRequest)) {
      return false;
    }
    ReservationRequest reservationRequest = (ReservationRequest) o;
    return Objects.equals(userId, reservationRequest.userId);
  }

  @Override
  public String toString() {
    return "{" +
        " userId='" + getUserId() + "'" +
        "}";
  }

}
