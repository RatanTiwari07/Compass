package com.byteforce.compass.controller;

import com.byteforce.compass.dto.AuthRequest;
import com.byteforce.compass.dto.AuthResponse;
import com.byteforce.compass.model.Counsellor;
import com.byteforce.compass.model.User;
import com.byteforce.compass.service.CounsellorService;
import com.byteforce.compass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CounsellorService counsellorService;
    
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        User user = userService.authenticateUser(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(new AuthResponse(user.getId(), user.getName(), user.getEmail(), "USER"));
    }
    
    @PostMapping("/counsellor/register")
    public ResponseEntity<Counsellor> registerCounsellor(@RequestBody Counsellor counsellor) {
        return ResponseEntity.ok(counsellorService.registerCounsellor(counsellor));
    }
    
    @PostMapping("/counsellor/login")
    public ResponseEntity<AuthResponse> counsellorLogin(@RequestBody AuthRequest authRequest) {
        Counsellor counsellor = counsellorService.authenticateCounsellor(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(new AuthResponse(counsellor.getId(), counsellor.getName(), null, "COUNSELLOR"));
    }
}
