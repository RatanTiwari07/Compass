package com.byteforce.compass.dto;

import com.byteforce.compass.model.AppointmentStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentResponse {
    private Long id;
    private Long userId;
    private String userName;
    private Long counsellorId;
    private String counsellorName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AppointmentStatus status;
    private Integer rating;
    private String feedback;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}