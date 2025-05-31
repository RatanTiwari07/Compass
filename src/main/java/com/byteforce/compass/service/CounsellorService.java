package com.byteforce.compass.service;

import com.byteforce.compass.model.Counsellor;
import com.byteforce.compass.repository.CounsellorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CounsellorService {

    @Autowired
    private CounsellorRepository counsellorRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Counsellor registerCounsellor(Counsellor counsellor) {
        counsellor.setPassword(passwordEncoder.encode(counsellor.getPassword()));
        return counsellorRepository.save(counsellor);
    }
    
    public Counsellor authenticateCounsellor(String email, String password) {
        Counsellor counsellor = counsellorRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));
        
        if (!passwordEncoder.matches(password, counsellor.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        
        return counsellor;
    }
}