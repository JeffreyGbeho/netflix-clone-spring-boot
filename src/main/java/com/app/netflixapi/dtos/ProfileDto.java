package com.app.netflixapi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileDto {
    @NotNull(message = "The name field is required")
    private String name;
    private boolean child = false;
}
