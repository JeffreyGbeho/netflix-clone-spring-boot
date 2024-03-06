package com.app.netflixapi.services;

import com.app.netflixapi.entities.Movie;
import com.app.netflixapi.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final ResourceLoader resourceLoader;
    private final MovieRepository movieRepository;

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
}
