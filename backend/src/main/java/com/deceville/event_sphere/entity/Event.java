package com.deceville.event_sphere.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "event")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
  @SequenceGenerator(name = "event_seq", sequenceName = "public.event_seq", allocationSize = 1)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Long capacity;

  @Column(nullable = false)
  private Date date;

  @Column(nullable = false)
  private String location;

  @JsonBackReference("event-organizer")
  @ManyToOne
  @JoinColumn(name = "organizer_id", nullable = false)
  private User organizer;

  @JsonManagedReference("event-reservation")
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "event", orphanRemoval = true)
  private List<Reservation> reservations;

  public Event() {
  }

  public Event(Long id, String title, String description, Long capacity, Date date, String location, User organizer,
      List<Reservation> reservations) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.capacity = capacity;
    this.date = date;
    this.location = location;
    this.organizer = organizer;
    this.reservations = reservations;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getCapacity() {
    return this.capacity;
  }

  public void setCapacity(Long capacity) {
    this.capacity = capacity;
  }

  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public User getOrganizer() {
    return this.organizer;
  }

  public void setOrganizer(User organizer) {
    this.organizer = organizer;
  }

  public List<Reservation> getReservations() {
    if (this.reservations != null) {
      return this.reservations.stream()
          .filter(reservation -> !reservation.isDeletedFlag())
          .collect(Collectors.toList());
    }
    return this.reservations;
  }

  public void setReservations(List<Reservation> reservations) {
    this.reservations = reservations;
  }

  public Event id(Long id) {
    setId(id);
    return this;
  }

  public Event title(String title) {
    setTitle(title);
    return this;
  }

  public Event description(String description) {
    setDescription(description);
    return this;
  }

  public Event capacity(Long capacity) {
    setCapacity(capacity);
    return this;
  }

  public Event date(Date date) {
    setDate(date);
    return this;
  }

  public Event location(String location) {
    setLocation(location);
    return this;
  }

  public Event organizer(User organizer) {
    setOrganizer(organizer);
    return this;
  }

  public Event reservations(List<Reservation> reservations) {
    setReservations(reservations);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Event)) {
      return false;
    }
    Event event = (Event) o;
    return Objects.equals(id, event.id) && Objects.equals(title, event.title)
        && Objects.equals(description, event.description) && Objects.equals(capacity, event.capacity)
        && Objects.equals(date, event.date) && Objects.equals(location, event.location)
        && Objects.equals(organizer, event.organizer) && Objects.equals(reservations, event.reservations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, capacity, date, location, organizer, reservations);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", title='" + getTitle() + "'" +
        ", description='" + getDescription() + "'" +
        ", capacity='" + getCapacity() + "'" +
        ", date='" + getDate() + "'" +
        ", location='" + getLocation() + "'" +
        ", organizer='" + getOrganizer() + "'" +
        ", reservations='" + getReservations() + "'" +
        "}";
  }
}
