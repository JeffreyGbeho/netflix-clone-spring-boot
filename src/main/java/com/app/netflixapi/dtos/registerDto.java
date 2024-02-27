package com.app.netflixapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class registerDto {
    private String email;
    private String password;
}
