package com.app.netflixapi.services;

import com.app.netflixapi.config.JwtService;
import com.app.netflixapi.dtos.AuthenticationResponse;
import com.app.netflixapi.entities.Role;
import com.app.netflixapi.entities.User;
import com.app.netflixapi.exceptions.UserAlreadyExists;
import com.app.netflixapi.repositories.UserRepository;
import com.app.netflixapi.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(String email, String password) {
        User userExists = this.userRepository.findByEmail(email).orElse(null);
        if (userExists != null) {
            throw new UserAlreadyExists("User already exists");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        Date now = new Date();
        newUser.setCreatedAt(now);
        newUser.setUpdatedAt(now);
        newUser.setRole(Role.USER);

        User user = this.userRepository.save(newUser);

        return generateToken(user);
    }

    @Override
    public AuthenticationResponse login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return generateToken((User) authentication.getPrincipal());
    }

    private AuthenticationResponse generateToken(User user) {
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }
}
