package com.app.netflixapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private boolean child;
    private String pictureUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    public User getUser() {
        return user;
    }

    @ManyToMany@JoinTable(
            name = "profile_movie",
            joinColumns = { @JoinColumn(name = "profile_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    private Set<Movie> favourites = new HashSet<>();

//    @JsonIgnore
//    public Set<Movie> getFavourites() {
//        return favourites;
//    }
}
