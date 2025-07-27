package com.cdac.repository;

import com.cdac.model.Attendance;
import com.cdac.model.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Find attendance records for a specific member
    List<Attendance> findByMemberDetails(MemberDetails memberDetails);

    // Find attendance record for a specific member on a specific date (useful for checking if already checked in)
    Optional<Attendance> findByMemberDetailsAndAttendanceDate(MemberDetails memberDetails, LocalDate attendanceDate);
}