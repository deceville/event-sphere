package com.deceville.event_sphere.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deceville.event_sphere.dto.ReservationDTO;
import com.deceville.event_sphere.entity.Reservation;
import com.deceville.event_sphere.entity.User;
import com.deceville.event_sphere.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

  @Autowired
  private ReservationService reservationService;

  @GetMapping
  public ResponseEntity<List<Reservation>> getAllReservations() {
    List<Reservation> reservations = reservationService.getAllReservations();
    if (reservations.isEmpty()) {
      return ResponseEntity.noContent().build(); // Return HTTP 204 if no data
    }
    return ResponseEntity.ok(reservations); // Return HTTP 200 with data
  }

  @GetMapping("/{id}")
  public ResponseEntity<List<Reservation>> getReservationByEventId(@PathVariable Long id) {
    List<Reservation> reservations = reservationService.getReservationsByEventId(id);
    if (reservations.isEmpty()) {
      return ResponseEntity.noContent().build(); // 204 No Content if no reservations found
    }
    return ResponseEntity.ok(reservations); // 200 OK with the list of reservations
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<List<ReservationDTO>> getReservationByUserId(@PathVariable Long id) {
    List<Reservation> reservations = reservationService.getActiveReservationsByUserId(id);
    // Convert Reservation to ReservationDTO
    List<ReservationDTO> reservationDTOs = reservations.stream()
        .map(reservation -> new ReservationDTO(
            reservation.getId(),
            reservation.getReservationTime(),
            reservation.getEvent().getId().toString(),
            reservation.getUser().getId().toString()))
        .collect(Collectors.toList());

    return ResponseEntity.ok(reservationDTOs);
  }

  @DeleteMapping("/{id}/cancel")
  public ResponseEntity<String> cancelReservation(
      @PathVariable Long id,
      @AuthenticationPrincipal User user) throws AccessDeniedException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().matches("ROLE_(AUDIENCE|ADMIN)"))) {
      throw new AccessDeniedException("User is not authorized to delete events");
    }

    String message = reservationService.cancelReservation(id, user.getId());
    if (message.equals("Reservation not found or not owned by the user.")) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    return ResponseEntity.ok(message);
  }
}
