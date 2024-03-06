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

}
