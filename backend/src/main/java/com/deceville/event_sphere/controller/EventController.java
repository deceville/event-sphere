package com.deceville.event_sphere.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deceville.event_sphere.dto.ReservationRequest;
import com.deceville.event_sphere.entity.Event;
import com.deceville.event_sphere.entity.User;
import com.deceville.event_sphere.service.EventService;

@RestController
@RequestMapping("/events")
public class EventController {

  @Autowired
  private EventService eventService;

  @GetMapping("/view")
  public ResponseEntity<List<Event>> getAllEvents() {
    List<Event> events = eventService.getAllEvents();
    if (events.isEmpty()) {
      return ResponseEntity.noContent().build(); // Return HTTP 204 if no data
    }
    return ResponseEntity.ok(events); // Return HTTP 200 with data
  }

  @PostMapping("/manage")
  public ResponseEntity<?> saveEvent(@RequestBody Event event) throws AccessDeniedException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().matches("ROLE_(ORGANIZER|ADMIN)"))) {
      throw new AccessDeniedException("User is not authorized to create events");
    }

    User currentUser = (User) authentication.getPrincipal();
    event.setOrganizer(currentUser);
    eventService.saveEvent(event);
    return ResponseEntity.status(HttpStatus.CREATED).body(event);
  }

  @GetMapping("/view/{id}")
  public ResponseEntity<Event> getEventById(@PathVariable Long id) throws AccessDeniedException {
    return eventService.getEventById(id)
        .map(event -> ResponseEntity.ok(event))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/manage/{id}")
  public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent)
      throws AccessDeniedException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().matches("ROLE_(ORGANIZER|ADMIN)"))) {
      throw new AccessDeniedException("User is not authorized to update events");
    }

    User currentUser = (User) authentication.getPrincipal();
    return eventService.getEventById(id)
        .map(event -> {
          event.setTitle(updatedEvent.getTitle());
          event.setDescription(updatedEvent.getDescription());
          event.setDate(updatedEvent.getDate());
          event.setLocation(updatedEvent.getLocation());
          event.setOrganizer(currentUser);
          eventService.saveEvent(event);
          return ResponseEntity.ok(event);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/manage/{id}")
  public ResponseEntity<Void> deleteEvent(@PathVariable Long id) throws AccessDeniedException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().matches("ROLE_(ORGANIZER|ADMIN)"))) {
      throw new AccessDeniedException("User is not authorized to delete events");
    }

    eventService.deleteEventWithReservations(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/reserve/{id}")
  public ResponseEntity<?> registerReservation(@PathVariable Long id,
      @RequestBody ReservationRequest reservationRequest) {
    String message = eventService.saveReservation(id, reservationRequest.getUserId());

    if (message.equals("Event is full. Cannot register reservation.")) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(reservationRequest);
  }
}
