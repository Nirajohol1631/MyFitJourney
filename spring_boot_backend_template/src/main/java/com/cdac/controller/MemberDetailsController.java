package com.cdac.controller;

import com.cdac.dto.MemberDetailsRequest;
import com.cdac.exception.ResourceNotFoundException;
import com.cdac.model.MemberDetails;
import com.cdac.model.MembershipPlan;
import com.cdac.model.Trainer;
import com.cdac.model.User;
import com.cdac.repository.MemberDetailsRepository;
import com.cdac.repository.MembershipPlanRepository;
import com.cdac.repository.TrainerRepository;
import com.cdac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/member-details")
public class MemberDetailsController {

    @Autowired
    private MemberDetailsRepository memberDetailsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MembershipPlanRepository membershipPlanRepository;
    @Autowired
    private TrainerRepository trainerRepository;

    // CREATE (Updated to use ResourceNotFoundException for dependencies)
    @PostMapping
    public ResponseEntity<?> createMemberDetails(@RequestBody MemberDetailsRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getUserId()));

        if (memberDetailsRepository.findByUser(user).isPresent()) {
            return new ResponseEntity<>("User already has member details.", HttpStatus.CONFLICT);
        }

        MembershipPlan plan = membershipPlanRepository.findById(request.getMembershipPlanId())
            .orElseThrow(() -> new ResourceNotFoundException("Membership Plan not found with ID: " + request.getMembershipPlanId()));

        Trainer trainer = null;
        if (request.getAssignedTrainerId() != null) {
            trainer = trainerRepository.findById(request.getAssignedTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Assigned Trainer not found with ID: " + request.getAssignedTrainerId()));
        }

        MemberDetails memberDetails = new MemberDetails();
        memberDetails.setUser(user);
        memberDetails.setCurrentMembership(plan);
        memberDetails.setAssignedTrainer(trainer);
        memberDetails.setMembershipStartDate(LocalDate.now());
        memberDetails.setMembershipEndDate(LocalDate.now().plusMonths(plan.getDurationMonths()));
        memberDetails.setActive(true);

        MemberDetails savedMemberDetails = memberDetailsRepository.save(memberDetails);
        return new ResponseEntity<>(savedMemberDetails, HttpStatus.CREATED);
    }

    // READ All (No changes needed)
    @GetMapping
    public ResponseEntity<List<MemberDetails>> getAllMemberDetails() {
        List<MemberDetails> details = memberDetailsRepository.findAll();
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    // READ by ID (Updated)
    @GetMapping("/{id}")
    public ResponseEntity<MemberDetails> getMemberDetailsById(@PathVariable Long id) {
        MemberDetails details = memberDetailsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Member Details not found with id: " + id));
        return ResponseEntity.ok(details);
    }

    // READ by User ID (Updated)
    @GetMapping("/user/{userId}")
    public ResponseEntity<MemberDetails> getMemberDetailsByUserId(@PathVariable Long userId) {
        MemberDetails details = memberDetailsRepository.findByUser_Id(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Member Details not found for user id: " + userId));
        return ResponseEntity.ok(details);
    }

    // UPDATE (Updated)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMemberDetails(@PathVariable Long id, @RequestBody MemberDetails memberDetailsUpdates) {
        MemberDetails existingDetails = memberDetailsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Member Details not found with id: " + id));

        if (memberDetailsUpdates.getCurrentMembership() != null && memberDetailsUpdates.getCurrentMembership().getId() != null) {
            MembershipPlan plan = membershipPlanRepository.findById(memberDetailsUpdates.getCurrentMembership().getId())
                .orElseThrow(() -> new ResourceNotFoundException("New Membership Plan not found with id: " + memberDetailsUpdates.getCurrentMembership().getId()));
            existingDetails.setCurrentMembership(plan);
            existingDetails.setMembershipEndDate(LocalDate.now().plusMonths(plan.getDurationMonths()));
        }

        if (memberDetailsUpdates.getAssignedTrainer() != null && memberDetailsUpdates.getAssignedTrainer().getId() != null) {
            Trainer trainer = trainerRepository.findById(memberDetailsUpdates.getAssignedTrainer().getId())
                .orElseThrow(() -> new ResourceNotFoundException("New Assigned Trainer not found with id: " + memberDetailsUpdates.getAssignedTrainer().getId()));
            existingDetails.setAssignedTrainer(trainer);
        } else if (memberDetailsUpdates.getAssignedTrainer() != null && memberDetailsUpdates.getAssignedTrainer().getId() == null) {
            existingDetails.setAssignedTrainer(null);
        }

        existingDetails.setActive(memberDetailsUpdates.isActive());

        MemberDetails updatedDetails = memberDetailsRepository.save(existingDetails);
        return ResponseEntity.ok(updatedDetails);
    }

    // DELETE (Updated)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberDetails(@PathVariable Long id) {
        MemberDetails details = memberDetailsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Member Details not found with id: " + id));
        
        memberDetailsRepository.delete(details);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
