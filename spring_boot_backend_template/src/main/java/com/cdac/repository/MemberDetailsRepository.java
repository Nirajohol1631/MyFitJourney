package com.cdac.repository;

import com.cdac.model.MemberDetails;
import com.cdac.model.User; // Import User for custom query if needed
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberDetailsRepository extends JpaRepository<MemberDetails, Long> {
    // Find MemberDetails by the associated User's ID
    Optional<MemberDetails> findByUser_Id(Long userId);
    // Find MemberDetails by the associated User object
    Optional<MemberDetails> findByUser(User user);
}