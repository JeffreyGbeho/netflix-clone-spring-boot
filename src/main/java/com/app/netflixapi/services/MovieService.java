package com.app.netflixapi.services;

import com.app.netflixapi.config.AuthenticationUserProvider;
import com.app.netflixapi.entities.Category;
import com.app.netflixapi.entities.Movie;
import com.app.netflixapi.entities.User;
import com.app.netflixapi.repositories.CategoryRepository;
import com.app.netflixapi.repositories.MovieRepository;
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

    public void addMovieToList(Long id) {
        String email = authenticationUserProvider.getAuthenticatedEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Movie movie = movieRepository.findById(id).orElseThrow();
        Set<Movie> fav = user.getFavourites();
        fav.add(movie);
        user.setFavourites(fav);

        userRepository.save(user);
    }

    public void removeMovieToList(Long id) {
        String email = authenticationUserProvider.getAuthenticatedEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Movie movie = movieRepository.findById(id).orElseThrow();
        Set<Movie> fav = user.getFavourites();
        fav.remove(movie);
        user.setFavourites(fav);

        userRepository.save(user);
    }

    public Set<Movie> getFavourites() {
        String email = authenticationUserProvider.getAuthenticatedEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user.getFavourites();
    }

    public Set<Movie> getMoviesByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow();

        return category.getMovies();
    }
}
