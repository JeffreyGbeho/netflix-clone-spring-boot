package com.app.netflixapi.services.interfaces;
import com.app.netflixapi.dtos.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse register(String email, String password);

    AuthenticationResponse login(String email, String password);
}
