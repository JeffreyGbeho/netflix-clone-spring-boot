package com.app.netflixapi.config;

import com.app.netflixapi.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUserProvider {
    public String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            User userAuthenticated = (User) authentication.getPrincipal();
            return userAuthenticated.getEmail();
        }

        return null;
    }
}
