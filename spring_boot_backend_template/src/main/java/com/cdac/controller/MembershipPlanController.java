package com.cdac.controller;

import com.cdac.exception.ResourceNotFoundException;
import com.cdac.model.MembershipPlan;
import com.cdac.repository.MembershipPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/membership-plans")
public class MembershipPlanController {

    @Autowired
    private MembershipPlanRepository membershipPlanRepository;

    // CREATE (No changes needed)
    @PostMapping
    public ResponseEntity<?> createPlan(@RequestBody MembershipPlan plan) {
        if (membershipPlanRepository.findByName(plan.getName()).isPresent()) {
            return new ResponseEntity<>("A plan with the name '" + plan.getName() + "' already exists.", HttpStatus.CONFLICT);
        }
        MembershipPlan savedPlan = membershipPlanRepository.save(plan);
        return new ResponseEntity<>(savedPlan, HttpStatus.CREATED);
    }

    // READ All (No changes needed)
    @GetMapping
    public ResponseEntity<List<MembershipPlan>> getAllPlans() {
        List<MembershipPlan> plans = membershipPlanRepository.findAll();
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    // READ by ID (Updated)
    @GetMapping("/{id}")
    public ResponseEntity<MembershipPlan> getPlanById(@PathVariable Long id) {
        MembershipPlan plan = membershipPlanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Membership Plan not found with id: " + id));
        return ResponseEntity.ok(plan);
    }

    // UPDATE (Updated)
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable Long id, @RequestBody MembershipPlan planDetails) {
        MembershipPlan existingPlan = membershipPlanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Membership Plan not found with id: " + id));

        Optional<MembershipPlan> existingPlanWithName = membershipPlanRepository.findByName(planDetails.getName());
        if (existingPlanWithName.isPresent() && !existingPlanWithName.get().getId().equals(id)) {
            return new ResponseEntity<>("A plan with the name '" + planDetails.getName() + "' already exists.", HttpStatus.CONFLICT);
        }

        existingPlan.setName(planDetails.getName());
        existingPlan.setDescription(planDetails.getDescription());
        existingPlan.setCost(planDetails.getCost());
        existingPlan.setDurationMonths(planDetails.getDurationMonths());
        
        MembershipPlan updatedPlan = membershipPlanRepository.save(existingPlan);
        return ResponseEntity.ok(updatedPlan);
    }

    // DELETE (Updated)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        MembershipPlan plan = membershipPlanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Membership Plan not found with id: " + id));
        
        membershipPlanRepository.delete(plan);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
