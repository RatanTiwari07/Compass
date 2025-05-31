package com.byteforce.compass.controller;

import com.byteforce.compass.dto.AppointmentRequest;
import com.byteforce.compass.dto.AppointmentResponse;
import com.byteforce.compass.dto.RatingRequest;
import com.byteforce.compass.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/appointments")
public class UserAppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    
    @PostMapping
    public ResponseEntity<AppointmentResponse> requestAppointment(
            @PathVariable Long userId,
            @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.requestAppointment(userId, request));
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<AppointmentResponse>> getUpcomingAppointments(@PathVariable Long userId) {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointmentsByUser(userId));
    }
    
    @GetMapping("/past")
    public ResponseEntity<List<AppointmentResponse>> getPastAppointments(@PathVariable Long userId) {
        return ResponseEntity.ok(appointmentService.getPastAppointmentsByUser(userId));
    }
    
    @PostMapping("/rate")
    public ResponseEntity<AppointmentResponse> rateAppointment(
            @PathVariable Long userId,
            @RequestBody RatingRequest request) {
        return ResponseEntity.ok(appointmentService.rateAppointment(userId, request));
    }
}