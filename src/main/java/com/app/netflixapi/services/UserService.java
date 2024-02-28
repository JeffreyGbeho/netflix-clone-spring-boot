package com.app.netflixapi.services;

import com.app.netflixapi.config.AuthenticationUserProvider;
import com.app.netflixapi.entities.User;
import com.app.netflixapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationUserProvider authenticationUserProvider;
    private final UserRepository userRepository;

    public User getAuthenticatedUser() {
        String email = this.authenticationUserProvider.getAuthenticatedEmail();

        if (email == null) {
            throw new RuntimeException("No email found");
        }

        return this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
