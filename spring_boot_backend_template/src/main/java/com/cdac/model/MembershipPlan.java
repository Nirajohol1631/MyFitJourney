package com.cdac.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "membership_plans") // Table name in DB
public class MembershipPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // e.g., "Monthly Basic", "Annual Premium"

    private String description;

    @Column(nullable = false)
    private double cost;

    @Column(nullable = false)
    private int durationMonths; // e.g., 1 for monthly, 12 for annual
}