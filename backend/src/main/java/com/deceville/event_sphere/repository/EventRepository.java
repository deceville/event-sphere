package com.deceville.event_sphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deceville.event_sphere.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
  @Query("SELECT e FROM Event e JOIN e.reservations r WHERE r.deletedFlag = false")
  List<Event> findEventsWithActiveReservations();
}
