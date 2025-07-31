package com.parceldelivery.backend.repository;

import com.parceldelivery.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCustomerId(String customerId);
    Optional<User> findByEmail(String email);
    Boolean existsByCustomerId(String customerId);
    Boolean existsByEmail(String email);
}