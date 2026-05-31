package com.example.loadtesttarget.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public record LoginRequest(String username, String password) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if ("password".equals(request.password()) &&
                request.username() != null && !request.username().isEmpty()) {
            return ResponseEntity.ok(new AuthResponse("fake-jwt-token-" + request.username()));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    public record AuthResponse(String token) {}
}