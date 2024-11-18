package com.deceville.event_sphere.dto;
import java.time.LocalDateTime;
import java.util.Objects;

public class ReservationDTO {
  private Long id;
  private LocalDateTime reservationTime;
  private String eventId;
  private String userId;

  public ReservationDTO() {}

  public ReservationDTO(Long id, LocalDateTime reservationTime, String eventId, String userId) {
    this.id = id;
    this.reservationTime = reservationTime;
    this.eventId = eventId;
    this.userId = userId;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getReservationTime() {
    return this.reservationTime;
  }

  public void setReservationTime(LocalDateTime reservationTime) {
    this.reservationTime = reservationTime;
  }

  public String getEventId() {
    return this.eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public ReservationDTO id(Long id) {
    setId(id);
    return this;
  }

  public ReservationDTO reservationTime(LocalDateTime reservationTime) {
    setReservationTime(reservationTime);
    return this;
  }

  public ReservationDTO eventId(String eventId) {
    setEventId(eventId);
    return this;
  }

  public ReservationDTO userId(String userId) {
    setUserId(userId);
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ReservationDTO)) {
            return false;
        }
        ReservationDTO reservationDTO = (ReservationDTO) o;
        return Objects.equals(id, reservationDTO.id) && Objects.equals(reservationTime, reservationDTO.reservationTime) && Objects.equals(eventId, reservationDTO.eventId) && Objects.equals(userId, reservationDTO.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, reservationTime, eventId, userId);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", reservationTime='" + getReservationTime() + "'" +
      ", eventId='" + getEventId() + "'" +
      ", userId='" + getUserId() + "'" +
      "}";
  }
}