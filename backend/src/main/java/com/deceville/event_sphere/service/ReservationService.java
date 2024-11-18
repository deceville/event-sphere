package com.deceville.event_sphere.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deceville.event_sphere.entity.Reservation;
import com.deceville.event_sphere.repository.ReservationRepository;

@Service
public class ReservationService {

  @Autowired
  private ReservationRepository reservationRepository;

  public List<Reservation> getAllReservations() {
    return reservationRepository.findAll();
  }

  public List<Reservation> getReservationsByEventId(Long id) {
    return reservationRepository.findByEventId(id);
  }

  public List<Reservation> getReservationsByUserId(Long id) {
    return reservationRepository.findAllByUserId(id);
  }

  public List<Reservation> getActiveReservationsByUserId(Long id) {
    return reservationRepository.findAllByUserIdAndDeletedFlagFalse(id);
  }

  public String cancelReservation(Long reservationId, Long userId) {
    boolean exists = reservationRepository.existsByIdAndUserId(reservationId, userId);
    if (!exists) {
      return "Reservation not found or not owned by the user.";
    }

    reservationRepository.softDeleteReservation(reservationId);
    return "Reservation cancelled successfully.";
  }
}
