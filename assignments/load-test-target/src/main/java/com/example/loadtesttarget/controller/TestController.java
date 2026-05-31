package com.example.loadtesttarget.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/echo/{message}")
    public ResponseEntity<String> echo(@PathVariable String message) {
        return ResponseEntity.ok(message);
    }

    @PostMapping("/compute")
    public ResponseEntity<Long> compute(@RequestBody ComputeRequest request) {
        long result = request.value() * request.multiplier();
        return ResponseEntity.ok(result);
    }

    public record ComputeRequest(long value, long multiplier) {}
}