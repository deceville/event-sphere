package com.deceville.event_sphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deceville.event_sphere.entity.Reservation;

import jakarta.transaction.Transactional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  List<Reservation> findByEventId(Long eventId);

  List<Reservation> findAllByUserId(Long userId);

  List<Reservation> findAllByUserIdAndDeletedFlagFalse(Long userId);

  long countByEventIdAndDeletedFlagFalse(Long eventId);

  boolean existsByIdAndUserId(Long reservationId, Long userId);

  List<Reservation> findByDeletedFlagFalse();

  @Transactional
  @Modifying
  @Query("UPDATE Reservation r SET r.deletedFlag = true WHERE r.id = :id AND r.deletedFlag = false")
  int softDeleteReservation(@Param("id") Long id);
}
