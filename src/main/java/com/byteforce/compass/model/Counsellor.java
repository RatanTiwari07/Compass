package com.byteforce.compass.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "counsellors")
public class Counsellor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String gender;
    private int age;
    private String mobileNo;
    private String email;
    private String password;
    
    @ElementCollection
    private List<String> specialities;
}