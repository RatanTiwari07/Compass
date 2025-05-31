package com.byteforce.compass.repository;

import com.byteforce.compass.model.Appointment;
import com.byteforce.compass.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserIdAndStatusIn(Long userId, List<AppointmentStatus> statuses);
    
    List<Appointment> findByCounsellorIdAndStatusIn(Long counsellorId, List<AppointmentStatus> statuses);
    
    @Query("SELECT a FROM Appointment a WHERE a.counsellor.id = :counsellorId AND a.status = 'REQUESTED'")
    List<Appointment> findPendingAppointmentsByCounsellor(Long counsellorId);
    
    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId AND a.startTime > :now AND a.status = 'APPROVED'")
    List<Appointment> findUpcomingAppointmentsByUser(Long userId, LocalDateTime now);
    
    @Query("SELECT a FROM Appointment a WHERE a.counsellor.id = :counsellorId AND a.startTime > :now AND a.status = 'APPROVED'")
    List<Appointment> findUpcomingAppointmentsByCounsellor(Long counsellorId, LocalDateTime now);
    
    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId AND a.startTime < :now AND a.status = 'COMPLETED'")
    List<Appointment> findPastAppointmentsByUser(Long userId, LocalDateTime now);
    
    @Query("SELECT a FROM Appointment a WHERE a.counsellor.id = :counsellorId AND a.startTime < :now AND a.status = 'COMPLETED'")
    List<Appointment> findPastAppointmentsByCounsellor(Long counsellorId, LocalDateTime now);
}