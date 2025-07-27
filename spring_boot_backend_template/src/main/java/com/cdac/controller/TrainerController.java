package com.cdac.controller;

import com.cdac.model.Trainer;
import com.cdac.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cdac.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    @Autowired
    private TrainerRepository trainerRepository;

    // CREATE: Add a new trainer
    @PostMapping
    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
        Trainer savedTrainer = trainerRepository.save(trainer);
        return new ResponseEntity<>(savedTrainer, HttpStatus.CREATED);
    }

    // READ: Get all trainers
    @GetMapping
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }

    // READ: Get trainer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Long id) {
        Trainer trainer = trainerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id)); // <-- This is the new line
        return ResponseEntity.ok(trainer);
    }

    // UPDATE: Update an existing trainer
    @PutMapping("/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable Long id, @RequestBody Trainer trainerDetails) {
        Trainer existingTrainer = trainerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id));

        // Update the existing trainer's details
        existingTrainer.setFullName(trainerDetails.getFullName());
        existingTrainer.setSpecialization(trainerDetails.getSpecialization());
        existingTrainer.setContactInfo(trainerDetails.getContactInfo());
        
        Trainer updatedTrainer = trainerRepository.save(existingTrainer);
        return ResponseEntity.ok(updatedTrainer);
    }

    // DELETE: Delete a trainer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        Trainer trainer = trainerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id));

        trainerRepository.delete(trainer); // Use the found entity to delete
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}