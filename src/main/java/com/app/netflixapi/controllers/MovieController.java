package com.app.netflixapi.controllers;

import com.app.netflixapi.entities.Movie;
import com.app.netflixapi.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {
   private final MovieService movieService;

    @GetMapping(value = "/{title}", produces = "video/mp4")
    public Mono<Resource> getMovie(@PathVariable String title) {
        return movieService.getMovie(title);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAll() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PutMapping("/{id}/favourite/add")
    public void addMovieToList(@PathVariable Long id) {
        movieService.addMovieToList(id);
    }

    @PutMapping("/{id}/favourite/remove")
    public void removeMovieToList(@PathVariable Long id) {
        movieService.removeMovieToList(id);
    }

    @GetMapping("/favourites")
    public ResponseEntity<Set<Movie>> getFavourites() {
        return ResponseEntity.ok(movieService.getFavourites());
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Set<Movie>> getMoviesByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(movieService.getMoviesByCategory(categoryName));
    }
}
