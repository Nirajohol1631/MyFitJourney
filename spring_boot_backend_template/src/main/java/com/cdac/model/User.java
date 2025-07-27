package com.cdac.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails { // <-- This is the main change

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    
    // --- All methods below this line are required by UserDetails ---
    
    /**
     * Returns the authorities granted to the user.
     * We convert our UserRole enum to a Spring Security GrantedAuthority.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // The role must be prefixed with "ROLE_" for Spring Security's role-based authorization to work.
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Returns the password used to authenticate the user.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // We'll keep it simple and return true.
    }

    /**
     * Indicates whether the user is locked or unlocked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // We'll keep it simple and return true.
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // We'll keep it simple and return true.
    }

    /**
     * Indicates whether the user is enabled or disabled.
     */
    @Override
    public boolean isEnabled() {
        return true; // We'll keep it simple and return true.
    }
}