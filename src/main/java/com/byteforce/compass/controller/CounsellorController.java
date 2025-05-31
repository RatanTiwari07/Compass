package com.byteforce.compass.controller;

import com.byteforce.compass.model.Counsellor;
import com.byteforce.compass.repository.CounsellorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counsellors")
public class CounsellorController {

    @Autowired
    private CounsellorRepository counsellorRepository;
    
    @GetMapping
    public List<Counsellor> getAllCounsellors() {
        return counsellorRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Counsellor> getCounsellorById(@PathVariable Long id) {
        return counsellorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Counsellor createCounsellor(@RequestBody Counsellor counsellor) {
        return counsellorRepository.save(counsellor);
    }
}