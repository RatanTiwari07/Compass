package com.byteforce.compass.dto;

import lombok.Data;

@Data
public class RatingRequest {
    private Long appointmentId;
    private Integer rating;
    private String feedback;
}