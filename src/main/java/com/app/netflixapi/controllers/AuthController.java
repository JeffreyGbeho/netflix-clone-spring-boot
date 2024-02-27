package com.app.netflixapi.controllers;

import com.app.netflixapi.dtos.registerDto;
import com.app.netflixapi.entities.User;
import com.app.netflixapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody registerDto registerDto) {
        return this.authService.register(registerDto);
    }

    @PostMapping("/login")
    public Object login(@RequestBody registerDto registerDto) {
        return this.authService.login(registerDto);
    }
}
