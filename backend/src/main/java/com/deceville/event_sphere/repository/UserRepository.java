package com.deceville.event_sphere.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deceville.event_sphere.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
