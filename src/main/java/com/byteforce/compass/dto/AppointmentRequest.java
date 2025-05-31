package com.byteforce.compass.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private Long counsellorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}