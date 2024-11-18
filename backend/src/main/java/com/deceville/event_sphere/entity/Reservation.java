package com.deceville.event_sphere.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "reservation")
public class Reservation {
  
  @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq")
	@SequenceGenerator(name = "reservation_seq", sequenceName = "public.reservation_seq", allocationSize = 1)
  private Long id;

  @Column(name = "reservation_time")
  private LocalDateTime reservationTime;

  @Column(name = "deleted_flag", nullable = false)
  private boolean deletedFlag = false;
  
  @JsonBackReference("user-reservation")
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @JsonBackReference("event-reservation")
  @ManyToOne
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  public Reservation() { }

  public Reservation(Long id, LocalDateTime reservationTime, boolean deletedFlag, User user, Event event) {
    this.id = id;
    this.reservationTime = reservationTime;
    this.deletedFlag = deletedFlag;
    this.user = user;
    this.event = event;
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

  public boolean isDeletedFlag() {
    return this.deletedFlag;
  }

  public boolean getDeletedFlag() {
    return this.deletedFlag;
  }

  public void setDeletedFlag(boolean deletedFlag) {
    this.deletedFlag = deletedFlag;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Event getEvent() {
    return this.event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public Reservation id(Long id) {
    setId(id);
    return this;
  }

  public Reservation reservationTime(LocalDateTime reservationTime) {
    setReservationTime(reservationTime);
    return this;
  }

  public Reservation deletedFlag(boolean deletedFlag) {
    setDeletedFlag(deletedFlag);
    return this;
  }

  public Reservation user(User user) {
    setUser(user);
    return this;
  }

  public Reservation event(Event event) {
    setEvent(event);
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Reservation)) {
            return false;
        }
        Reservation reservation = (Reservation) o;
        return Objects.equals(id, reservation.id) && Objects.equals(reservationTime, reservation.reservationTime) && deletedFlag == reservation.deletedFlag && Objects.equals(user, reservation.user) && Objects.equals(event, reservation.event);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, reservationTime, deletedFlag, user, event);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", reservationTime='" + getReservationTime() + "'" +
      ", deletedFlag='" + isDeletedFlag() + "'" +
      ", user='" + getUser() + "'" +
      ", event='" + getEvent() + "'" +
      "}";
  }
}
