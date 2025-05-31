package com.byteforce.compass.service;

import com.byteforce.compass.dto.AppointmentRequest;
import com.byteforce.compass.dto.AppointmentResponse;
import com.byteforce.compass.dto.RatingRequest;
import com.byteforce.compass.model.Appointment;
import com.byteforce.compass.model.AppointmentStatus;
import com.byteforce.compass.model.Counsellor;
import com.byteforce.compass.model.User;
import com.byteforce.compass.repository.AppointmentRepository;
import com.byteforce.compass.repository.CounsellorRepository;
import com.byteforce.compass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CounsellorRepository counsellorRepository;
    
    @Transactional
    public AppointmentResponse requestAppointment(Long userId, AppointmentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Counsellor counsellor = counsellorRepository.findById(request.getCounsellorId())
                .orElseThrow(() -> new RuntimeException("Counsellor not found"));
        
        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setCounsellor(counsellor);
        appointment.setStartTime(request.getStartTime());
        appointment.setEndTime(request.getEndTime());
        appointment.setStatus(AppointmentStatus.REQUESTED);
        
        appointment = appointmentRepository.save(appointment);
        
        return mapToAppointmentResponse(appointment);
    }
    
    @Transactional
    public AppointmentResponse approveAppointment(Long counsellorId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (!appointment.getCounsellor().getId().equals(counsellorId)) {
            throw new RuntimeException("Not authorized to approve this appointment");
        }
        
        appointment.setStatus(AppointmentStatus.APPROVED);
        appointment = appointmentRepository.save(appointment);
        
        return mapToAppointmentResponse(appointment);
    }
    
    @Transactional
    public AppointmentResponse rejectAppointment(Long counsellorId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (!appointment.getCounsellor().getId().equals(counsellorId)) {
            throw new RuntimeException("Not authorized to reject this appointment");
        }
        
        appointment.setStatus(AppointmentStatus.REJECTED);
        appointment = appointmentRepository.save(appointment);
        
        return mapToAppointmentResponse(appointment);
    }
    
    @Transactional
    public AppointmentResponse completeAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment = appointmentRepository.save(appointment);
        
        return mapToAppointmentResponse(appointment);
    }
    
    @Transactional
    public AppointmentResponse rateAppointment(Long userId, RatingRequest request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (!appointment.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to rate this appointment");
        }
        
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new RuntimeException("Cannot rate an appointment that is not completed");
        }
        
        appointment.setRating(request.getRating());
        appointment.setFeedback(request.getFeedback());
        appointment = appointmentRepository.save(appointment);
        
        return mapToAppointmentResponse(appointment);
    }
    
    public List<AppointmentResponse> getPendingAppointmentsByCounsellor(Long counsellorId) {
        return appointmentRepository.findPendingAppointmentsByCounsellor(counsellorId)
                .stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }
    
    public List<AppointmentResponse> getUpcomingAppointmentsByUser(Long userId) {
        return appointmentRepository.findUpcomingAppointmentsByUser(userId, LocalDateTime.now())
                .stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }
    
    public List<AppointmentResponse> getUpcomingAppointmentsByCounsellor(Long counsellorId) {
        return appointmentRepository.findUpcomingAppointmentsByCounsellor(counsellorId, LocalDateTime.now())
                .stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }
    
    public List<AppointmentResponse> getPastAppointmentsByUser(Long userId) {
        return appointmentRepository.findPastAppointmentsByUser(userId, LocalDateTime.now())
                .stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }
    
    public List<AppointmentResponse> getPastAppointmentsByCounsellor(Long counsellorId) {
        return appointmentRepository.findPastAppointmentsByCounsellor(counsellorId, LocalDateTime.now())
                .stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }
    
    public double getCounsellorAverageRating(Long counsellorId) {
        List<Appointment> completedAppointments = appointmentRepository.findByCounsellorIdAndStatusIn(
                counsellorId, Arrays.asList(AppointmentStatus.COMPLETED));
        
        return completedAppointments.stream()
                .filter(a -> a.getRating() != null)
                .mapToInt(Appointment::getRating)
                .average()
                .orElse(0.0);
    }
    
    private AppointmentResponse mapToAppointmentResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setUserId(appointment.getUser().getId());
        response.setUserName(appointment.getUser().getName());
        response.setCounsellorId(appointment.getCounsellor().getId());
        response.setCounsellorName(appointment.getCounsellor().getName());
        response.setStartTime(appointment.getStartTime());
        response.setEndTime(appointment.getEndTime());
        response.setStatus(appointment.getStatus());
        response.setRating(appointment.getRating());
        response.setFeedback(appointment.getFeedback());
        response.setCreatedAt(appointment.getCreatedAt());
        response.setUpdatedAt(appointment.getUpdatedAt());
        return response;
    }
}