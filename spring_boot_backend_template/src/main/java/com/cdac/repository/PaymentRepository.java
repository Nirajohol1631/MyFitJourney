package com.cdac.repository;

import com.cdac.model.Payment;
import com.cdac.model.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Find payment records for a specific member
    List<Payment> findByMemberDetails(MemberDetails memberDetails);
}