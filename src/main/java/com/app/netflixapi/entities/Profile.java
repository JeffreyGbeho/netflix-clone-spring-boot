package com.app.netflixapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long profileId;
    private String name;
    private boolean child;
    private String pictureUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "profile_movie", joinColumns = { @JoinColumn(name = "profileId") }, inverseJoinColumns = { @JoinColumn(name = "movieId") })
    private Set<Movie> favoriteMovies;
}
