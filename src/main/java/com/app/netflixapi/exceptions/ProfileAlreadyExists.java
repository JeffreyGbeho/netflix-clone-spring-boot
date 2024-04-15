package com.app.netflixapi.exceptions;

public class ProfileAlreadyExists extends RuntimeException {
    public ProfileAlreadyExists(String message) {
        super(message);
    }
}
