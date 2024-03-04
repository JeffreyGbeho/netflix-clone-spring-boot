package com.app.netflixapi.exceptions;

public class ProfileNotFound extends RuntimeException {
    public ProfileNotFound(String message) {
        super(message);
    }
}
