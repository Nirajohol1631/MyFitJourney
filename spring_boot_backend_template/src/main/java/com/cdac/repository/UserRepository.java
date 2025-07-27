package com.cdac.repository; // THIS PACKAGE MUST MATCH THE FOLDER STRUCTURE

import com.cdac.model.User; // <--- THIS IMPORT MUST MATCH YOUR User entity's package

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Tells Spring Data JPA to create an implementation for this interface
public interface UserRepository extends JpaRepository<User, Long> {
    // This interface extends JpaRepository to get basic CRUD operations for the User entity.
    // Spring Data JPA will automatically detect and implement this repository.
	Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}