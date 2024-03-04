package com.app.netflixapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private int categoryAge;
    private String author;
    private String url;
    private int duration;
}
