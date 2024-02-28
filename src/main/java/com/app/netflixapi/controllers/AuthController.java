package com.app.netflixapi.controllers;

import com.app.netflixapi.dtos.AuthResponse;
import com.app.netflixapi.dtos.RegisterDto;
import com.app.netflixapi.entities.User;
import com.app.netflixapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterDto registerDto) {
        String token = this.authService.register(registerDto);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody RegisterDto registerDto) {
        String token = this.authService.login(registerDto);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
