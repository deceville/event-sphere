package com.deceville.event_sphere.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User implements UserDetails {
  
  @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", sequenceName = "public.user_seq", allocationSize = 1)
  @Column(nullable = false)
  private Long id;
  
  @Column(unique = true, length = 20, nullable = false)
  private String username;
  
  @Column(nullable = false)
  private String firstname;
  
  @Column(nullable = false)
  private String lastname;
  
  @Column(nullable = false)
  private String password;
  
  @Column(nullable = false)
	@Enumerated(EnumType.STRING)
  private Role role;

  @CreationTimestamp
  @Column(updatable = false, name = "created_at")
  private LocalDateTime createdAt;

	@JsonManagedReference("user-reservation")
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reservation> reservations;

	@JsonManagedReference("event-organizer")
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "organizer")
  private List<Event> events;

	public User() {}

	public User(Long id, String username, String firstname, String lastname, String password, Role role, LocalDateTime createdAt, List<Reservation> reservations, List<Event> events) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.role = role;
		this.createdAt = createdAt;
		this.reservations = reservations;
		this.events = events;
	}

  @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
			return List.of(new SimpleGrantedAuthority(this.getRole().name()));
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<Reservation> getReservations() {
		return this.reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public User id(Long id) {
		setId(id);
		return this;
	}

	public User username(String username) {
		setUsername(username);
		return this;
	}

	public User firstname(String firstname) {
		setFirstname(firstname);
		return this;
	}

	public User lastname(String lastname) {
		setLastname(lastname);
		return this;
	}

	public User password(String password) {
		setPassword(password);
		return this;
	}

	public User role(Role role) {
		setRole(role);
		return this;
	}

	public User createdAt(LocalDateTime createdAt) {
		setCreatedAt(createdAt);
		return this;
	}

	public User reservations(List<Reservation> reservations) {
		setReservations(reservations);
		return this;
	}

	public User events(List<Event> events) {
		setEvents(events);
		return this;
	}

	@Override
		public boolean equals(Object o) {
				if (o == this)
						return true;
				if (!(o instanceof User)) {
						return false;
				}
				User user = (User) o;
				return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && Objects.equals(createdAt, user.createdAt) && Objects.equals(reservations, user.reservations) && Objects.equals(events, user.events);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, firstname, lastname, password, role, createdAt, reservations, events);
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", username='" + getUsername() + "'" +
			", firstname='" + getFirstname() + "'" +
			", lastname='" + getLastname() + "'" +
			", password='" + getPassword() + "'" +
			", role='" + getRole() + "'" +
			", createdAt='" + getCreatedAt() + "'" +
			", reservations='" + getReservations() + "'" +
			", events='" + getEvents() + "'" +
			"}";
	}	
}
