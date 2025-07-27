package com.cdac.repository;

import com.cdac.model.MembershipPlan;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
    // JpaRepository provides CRUD operations for MembershipPlan entity
    // You can add custom finders here if needed, e.g., Optional<MembershipPlan> findByName(String name);
	Optional<MembershipPlan> findByName(String name);
}