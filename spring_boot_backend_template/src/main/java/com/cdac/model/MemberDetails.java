package com.cdac.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore; // Import this!

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member_details")
public class MemberDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-One relationship with User
    // @JsonIgnore here to prevent serialization of the User proxy when fetching MemberDetails
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
    @JsonIgnore // <--- ADD THIS LINE AND IMPORT!
    private User user;

    // Many-to-One relationship with MembershipPlan
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "membership_plan_id", nullable = false)
    private MembershipPlan currentMembership;

    // Many-to-One relationship with Trainer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_trainer_id")
    private Trainer assignedTrainer;

    @Column(nullable = false)
    private LocalDate membershipStartDate;

    private LocalDate membershipEndDate;

    private boolean isActive;
}