package com.cdac.repository;

import com.cdac.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    // JpaRepository provides CRUD operations for Trainer entity
}