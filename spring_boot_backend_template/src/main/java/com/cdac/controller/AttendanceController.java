package com.cdac.controller;

import com.cdac.model.Attendance;
import com.cdac.model.MemberDetails;
import com.cdac.repository.AttendanceRepository;
import com.cdac.repository.MemberDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MemberDetailsRepository memberDetailsRepository;

    // Endpoint for member check-in
    @PostMapping("/check-in/{memberDetailsId}")
    public ResponseEntity<String> checkIn(@PathVariable Long memberDetailsId) {
        Optional<MemberDetails> optionalMemberDetails = memberDetailsRepository.findById(memberDetailsId);
        if (optionalMemberDetails.isEmpty()) {
            return new ResponseEntity<>("Member details not found.", HttpStatus.NOT_FOUND);
        }
        MemberDetails memberDetails = optionalMemberDetails.get();

        // Check if member already checked in today
        Optional<Attendance> existingAttendance = attendanceRepository.findByMemberDetailsAndAttendanceDate(memberDetails, LocalDate.now());
        if (existingAttendance.isPresent() && existingAttendance.get().getCheckOutTime() == null) {
            return new ResponseEntity<>("Member already checked in today.", HttpStatus.CONFLICT);
        }

        Attendance attendance = new Attendance();
        attendance.setMemberDetails(memberDetails);
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setCheckInTime(LocalTime.now());

        attendanceRepository.save(attendance);
        return new ResponseEntity<>("Checked in successfully!", HttpStatus.CREATED);
    }

    // Endpoint for member check-out
    @PutMapping("/check-out/{memberDetailsId}")
    public ResponseEntity<String> checkOut(@PathVariable Long memberDetailsId) {
        Optional<MemberDetails> optionalMemberDetails = memberDetailsRepository.findById(memberDetailsId);
        if (optionalMemberDetails.isEmpty()) {
            return new ResponseEntity<>("Member details not found.", HttpStatus.NOT_FOUND);
        }
        MemberDetails memberDetails = optionalMemberDetails.get();

        Optional<Attendance> existingAttendance = attendanceRepository.findByMemberDetailsAndAttendanceDate(memberDetails, LocalDate.now());
        if (existingAttendance.isEmpty() || existingAttendance.get().getCheckOutTime() != null) {
            return new ResponseEntity<>("Member not checked in or already checked out today.", HttpStatus.BAD_REQUEST);
        }

        Attendance attendance = existingAttendance.get();
        attendance.setCheckOutTime(LocalTime.now());

        attendanceRepository.save(attendance);
        return new ResponseEntity<>("Checked out successfully!", HttpStatus.OK);
    }

    // READ: Get all attendance records (Admin view)
    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        List<Attendance> attendanceRecords = attendanceRepository.findAll();
        return new ResponseEntity<>(attendanceRecords, HttpStatus.OK);
    }

    // READ: Get attendance records for a specific member
    @GetMapping("/member/{memberDetailsId}")
    public ResponseEntity<List<Attendance>> getAttendanceByMember(@PathVariable Long memberDetailsId) {
        Optional<MemberDetails> optionalMemberDetails = memberDetailsRepository.findById(memberDetailsId);
        if (optionalMemberDetails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Attendance> records = attendanceRepository.findByMemberDetails(optionalMemberDetails.get());
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // DELETE: Delete an attendance record (Admin only)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        if (attendanceRepository.existsById(id)) {
            attendanceRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}