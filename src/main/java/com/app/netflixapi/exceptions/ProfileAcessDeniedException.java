package com.app.netflixapi.exceptions;

public class ProfileAcessDeniedException extends RuntimeException {
    public ProfileAcessDeniedException(String message) {
        super(message);
    }
}
