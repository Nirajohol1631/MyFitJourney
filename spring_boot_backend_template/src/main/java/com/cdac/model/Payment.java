package com.cdac.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore; // Import JsonIgnore

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments") // Table name in DB
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-One relationship with MemberDetails (a MemberDetails can have many payments)
    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch
    @JoinColumn(name = "member_details_id", nullable = false)
    @JsonIgnore // <-- ADD THIS ANNOTATION
    private MemberDetails memberDetails;

    // Many-to-One relationship with MembershipPlan (a payment is for a specific plan)
    @ManyToOne(fetch = FetchType.EAGER) // Eager fetch for the plan details
    @JoinColumn(name = "membership_plan_id", nullable = false)
    private MembershipPlan paidForPlan;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDate paymentDate; // Date when payment was made

    private String paymentMethod; // e.g., "Cash", "Card", "Online"
}