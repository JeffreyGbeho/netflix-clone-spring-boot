package com.app.netflixapi.services;

import com.app.netflixapi.config.JwtProvider;
import com.app.netflixapi.dtos.RegisterDto;
import com.app.netflixapi.entities.User;
import com.app.netflixapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public String register(RegisterDto registerDto) {
        // Check if user already exists
        User userExists = this.userRepository.findByEmail(registerDto.getEmail()).orElse(null);

        if (userExists != null) {
            throw new RuntimeException("User already exists");
        }

        // Save the new user
        User newUser = new User();
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Date now = new Date();
        newUser.setCreatedAt(now);
        newUser.setUpdatedAt(now);

        User user = this.userRepository.save(newUser);

        return jwtProvider.createToken(user);
    }

    public String login(RegisterDto registerDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerDto.getEmail(), registerDto.getPassword()));
        String token = jwtProvider.createToken((User) authentication.getPrincipal());
        return token;
    }
}
