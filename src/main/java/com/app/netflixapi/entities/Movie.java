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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long moveId;
    private String title;
    private String description;
    private int categoryAge;
    private String author;
    private String url;
    private int duration;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_category", joinColumns = { @JoinColumn(name = "movieId") }, inverseJoinColumns = { @JoinColumn(name = "categoryId") })
    private Set<Category> categories;
}
