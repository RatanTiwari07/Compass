package com.byteforce.compass.controller;

import com.byteforce.compass.dto.AppointmentResponse;
import com.byteforce.compass.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/counsellors/{counsellorId}/appointments")
public class CounsellorAppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    
    @GetMapping("/pending")
    public ResponseEntity<List<AppointmentResponse>> getPendingAppointments(@PathVariable Long counsellorId) {
        return ResponseEntity.ok(appointmentService.getPendingAppointmentsByCounsellor(counsellorId));
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<AppointmentResponse>> getUpcomingAppointments(@PathVariable Long counsellorId) {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointmentsByCounsellor(counsellorId));
    }
    
    @GetMapping("/past")
    public ResponseEntity<List<AppointmentResponse>> getPastAppointments(@PathVariable Long counsellorId) {
        return ResponseEntity.ok(appointmentService.getPastAppointmentsByCounsellor(counsellorId));
    }
    
    @PostMapping("/{appointmentId}/approve")
    public ResponseEntity<AppointmentResponse> approveAppointment(
            @PathVariable Long counsellorId,
            @PathVariable Long appointmentId) {
        return ResponseEntity.ok(appointmentService.approveAppointment(counsellorId, appointmentId));
    }
    
    @PostMapping("/{appointmentId}/reject")
    public ResponseEntity<AppointmentResponse> rejectAppointment(
            @PathVariable Long counsellorId,
            @PathVariable Long appointmentId) {
        return ResponseEntity.ok(appointmentService.rejectAppointment(counsellorId, appointmentId));
    }
    
    @GetMapping("/rating")
    public ResponseEntity<Map<String, Double>> getCounsellorRating(@PathVariable Long counsellorId) {
        double averageRating = appointmentService.getCounsellorAverageRating(counsellorId);
        return ResponseEntity.ok(Map.of("averageRating", averageRating));
    }
}