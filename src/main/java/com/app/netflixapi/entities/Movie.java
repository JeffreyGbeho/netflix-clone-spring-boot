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
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Integer limitAge;
    private String author;
    private String url;
    private Integer duration;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "movie_categories",
            joinColumns = { @JoinColumn(name = "movie_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<Category> categories = new HashSet<>();

    @JsonIgnore
    public Set<Category> getCategories() {
        return categories;
    }
}
