package com.cdac.controller;

import com.cdac.model.Payment;
import com.cdac.model.MemberDetails;
import com.cdac.model.MembershipPlan;
import com.cdac.repository.PaymentRepository;
import com.cdac.repository.MemberDetailsRepository;
import com.cdac.repository.MembershipPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cdac.dto.PaymentRequest; // Add this import

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberDetailsRepository memberDetailsRepository;

    @Autowired
    private MembershipPlanRepository membershipPlanRepository; // To validate paidForPlan

    // CREATE: Record a new payment
    @PostMapping
    public ResponseEntity<String> recordPayment(@RequestBody PaymentRequest request) { // Changed parameter here
        // Validate memberDetails
        Optional<MemberDetails> optionalMemberDetails = memberDetailsRepository.findById(request.getMemberDetailsId()); // Use request.getMemberDetailsId()
        if (optionalMemberDetails.isEmpty()) {
            return new ResponseEntity<>("Member details not found for ID: " + request.getMemberDetailsId(), HttpStatus.BAD_REQUEST);
        }
        MemberDetails memberDetails = optionalMemberDetails.get();

        // Validate MembershipPlan
        Optional<MembershipPlan> optionalPlan = membershipPlanRepository.findById(request.getPaidForPlanId()); // Use request.getPaidForPlanId()
        if (optionalPlan.isEmpty()) {
            return new ResponseEntity<>("Membership plan not found for ID: " + request.getPaidForPlanId(), HttpStatus.BAD_REQUEST);
        }
        MembershipPlan paidForPlan = optionalPlan.get(); // Renamed to avoid conflict with 'plan'

        // Create the Payment entity
        Payment payment = new Payment(); // Create a new instance
        payment.setMemberDetails(memberDetails);
        payment.setPaidForPlan(paidForPlan);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());

        // Set payment date if not provided in request (or ensure it's valid)
        if (request.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        } else {
            payment.setPaymentDate(request.getPaymentDate());
        }

        paymentRepository.save(payment);
        return new ResponseEntity<>("Payment recorded successfully!", HttpStatus.CREATED);
    }

    // READ: Get payment records for a specific member
    @GetMapping("/member/{memberDetailsId}")
    public ResponseEntity<List<Payment>> getPaymentsByMember(@PathVariable Long memberDetailsId) {
        Optional<MemberDetails> optionalMemberDetails = memberDetailsRepository.findById(memberDetailsId);
        if (optionalMemberDetails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Payment> payments = paymentRepository.findByMemberDetails(optionalMemberDetails.get());
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    // DELETE: Delete a payment record (Admin only)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}