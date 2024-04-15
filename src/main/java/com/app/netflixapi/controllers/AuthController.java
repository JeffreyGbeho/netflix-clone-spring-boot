package com.app.netflixapi.controllers;

import com.app.netflixapi.dtos.AuthenticationRequest;
import com.app.netflixapi.dtos.AuthenticationResponse;
import com.app.netflixapi.dtos.RegisterRequest;
import com.app.netflixapi.services.interfaces.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            log.info("POST api/v1/register - START");
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request.getEmail(), request.getPassword()));
        } finally {
            log.info("POST api/v1/register - DONE");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        try {
            log.info("POST api/v1/login - START");
            return ResponseEntity.status(HttpStatus.OK).body(authService.login(request.getEmail(), request.getPassword()));
        } finally {
            log.info("POST api/v1/login - DONE");
        }
    }
}
