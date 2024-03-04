package com.app.netflixapi.services;

import com.app.netflixapi.config.JwtService;
import com.app.netflixapi.dtos.AuthenticationRequest;
import com.app.netflixapi.dtos.AuthenticationResponse;
import com.app.netflixapi.dtos.RegisterRequest;
import com.app.netflixapi.entities.Role;
import com.app.netflixapi.entities.User;
import com.app.netflixapi.exceptions.UserAlreadyExists;
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
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
        // Check if user already exists
        User userExists = this.userRepository.findByEmail(request.getEmail()).orElse(null);

        if (userExists != null) {
            throw new UserAlreadyExists("User already exists");
        }

        // Save the new user
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        Date now = new Date();
        newUser.setCreatedAt(now);
        newUser.setUpdatedAt(now);
        newUser.setRole(Role.USER);

        User user = this.userRepository.save(newUser);

        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = jwtService.generateToken((User) authentication.getPrincipal());
        return AuthenticationResponse.builder().token(token).build();
    }
}
