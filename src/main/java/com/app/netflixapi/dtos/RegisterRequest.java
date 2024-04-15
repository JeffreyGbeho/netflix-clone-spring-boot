package com.app.netflixapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotNull(message = "The email field is required")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "The password field is required")
    private String password;
}
