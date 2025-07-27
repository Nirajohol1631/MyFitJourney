package com.cdac.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trainers") // Table name in DB
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String specialization; // e.g., "Weightlifting", "Yoga", "Cardio"

    private String contactInfo; // e.g., phone or email

    // Optional: If a trainer also needs a user login, link to the User entity
    // @OneToOne
    // @JoinColumn(name = "user_id", referencedColumnName = "id")
    // private User user; // Link to User account if trainer has a login
}