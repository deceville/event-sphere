package com.deceville.event_sphere.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deceville.event_sphere.entity.Event;
import com.deceville.event_sphere.entity.Reservation;
import com.deceville.event_sphere.entity.User;
import com.deceville.event_sphere.repository.EventRepository;
import com.deceville.event_sphere.repository.ReservationRepository;
import com.deceville.event_sphere.repository.UserRepository;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private ReservationRepository reservationRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Event> getAllEvents() {
    return eventRepository.findAll();
  }
  
  public List<Event> getActiveEvents() {
    return eventRepository.findEventsWithActiveReservations();
  }

  public Event saveEvent(Event event) {
    return eventRepository.save(event);
  }

  public Optional<Event> getEventById(Long id) {
    return eventRepository.findById(id);
  }

  public void deleteEventWithReservations(Long eventId) {
    List<Reservation> reservations = reservationRepository.findByEventId(eventId);
    if (!reservations.isEmpty()) {
      reservationRepository.deleteAll(reservations);
    }

    eventRepository.deleteById(eventId);
  }

  public String saveReservation(Long eventId, Long userId) {
    // Fetch the event and user
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new RuntimeException("Event not found"));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    // Check if the event is full
    if (reservationRepository.countByEventIdAndDeletedFlagFalse(eventId) >= event.getCapacity()) {
      return "Event is full. Cannot register reservation.";
    }

    // Create a new reservation
    Reservation reservation = new Reservation();
    reservation.setUser(user);
    reservation.setEvent(event);
    reservation.setReservationTime(LocalDateTime.now());

    // Save the reservation
    reservationRepository.save(reservation);
    return "Reservation successful.";
  }
}
