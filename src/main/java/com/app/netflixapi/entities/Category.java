package com.app.netflixapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Movie> movies = new HashSet<>();

    @JsonIgnore
    public Set<Movie> getMovies() {
        return movies;
    }
}
