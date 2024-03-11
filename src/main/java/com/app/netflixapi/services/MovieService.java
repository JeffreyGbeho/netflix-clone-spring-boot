package com.app.netflixapi.services;

import com.app.netflixapi.config.AuthenticationUserProvider;
import com.app.netflixapi.entities.Category;
import com.app.netflixapi.entities.Movie;
import com.app.netflixapi.entities.Profile;
import com.app.netflixapi.entities.User;
import com.app.netflixapi.repositories.CategoryRepository;
import com.app.netflixapi.repositories.MovieRepository;
import com.app.netflixapi.repositories.ProfileRepository;
import com.app.netflixapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final ResourceLoader resourceLoader;
    private final MovieRepository movieRepository;
    private final AuthenticationUserProvider authenticationUserProvider;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileRepository profileRepository;

    private static final String FORMAT  = "classpath:videos/%s.mp4";

    public Mono<Resource> getMovie(String title) {
        return Mono.fromSupplier(() -> resourceLoader.getResource(String.format(FORMAT, title)));
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    public void addMovieToList(Long id, Long profileId) {
        Profile profile = this.getProfile(profileId);

        Movie movie = movieRepository.findById(id).orElseThrow();
        Set<Movie> fav = profile.getFavourites();
        fav.add(movie);
        profile.setFavourites(fav);

        profileRepository.save(profile);
    }

    public void removeMovieToList(Long id, Long profileId) {
        Profile profile = this.getProfile(profileId);

        Movie movie = movieRepository.findById(id).orElseThrow();
        Set<Movie> fav = profile.getFavourites();
        fav.remove(movie);
        profile.setFavourites(fav);

        profileRepository.save(profile);
    }

    public Set<Movie> getFavourites(Long profileId) {
        Profile profile = this.getProfile(profileId);

        return profile.getFavourites();
    }

    public Set<Movie> getMoviesByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow();

        return category.getMovies();
    }

    private Profile getProfile(Long profileId) {
        String email = authenticationUserProvider.getAuthenticatedEmail();

        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));

        if (!profile.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Profile not match with user");
        }
    }
}
