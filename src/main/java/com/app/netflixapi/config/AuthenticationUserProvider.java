package com.app.netflixapi.config;

import com.app.netflixapi.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUserProvider {
    public String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userAuthenticated = (UserDetails) authentication.getPrincipal();
            return userAuthenticated.getUsername();
        }

        return null;
    }
}
