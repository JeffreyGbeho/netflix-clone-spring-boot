package com.app.netflixapi.controllers;

import com.app.netflixapi.dtos.FavouriteRequest;
import com.app.netflixapi.entities.Movie;
import com.app.netflixapi.services.MovieServiceImpl;
import com.app.netflixapi.services.interfaces.MovieService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
@Validated
@Slf4j
public class MovieController {
   private final MovieService movieService;

    @GetMapping("/streaming/{movieId}")
    public ResponseEntity<StreamingResponseBody> getMovie(@PathVariable Long movieId) throws IOException {
        try {
            log.info("GET api/v1/movie/streaming/{} - START");
            StreamingResponseBody responseBody = movieService.streamingMovie(movieId);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(responseBody);
        } finally {
            log.info("GET api/v1/movie/streaming/{} - DONE");
        }
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAll() {
        try {
            log.info("GET api/v1/movie - START");
            return ResponseEntity.ok(movieService.getAllMovies());
        } finally {
            log.info("GET api/v1/movie - DONE");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        try {
            log.info("GET api/v1/movie/{} - START");
            return ResponseEntity.ok(movieService.getMovieById(id));
        } finally {
            log.info("GET api/v1/movie/{} - DONE");
        }

    }

    @PutMapping("/{id}/favourite/add")
    public void addMovieToList(@PathVariable Long id, @RequestBody FavouriteRequest request) {
        try {
            log.info("PUT api/v1/movie/{}/favourite/add - START");
            movieService.addMovieToList(id, request.getProfileId());
        } finally {
            log.info("PUT api/v1/movie/{}/favourite/add - DONE");
        }
    }

    @PutMapping("/{id}/favourite/remove")
    public void removeMovieToList(@PathVariable Long id, @RequestBody FavouriteRequest request) {
        try {
            log.info("PUT api/v1/movie/{}/favourite/remove - START");
            movieService.removeMovieToList(id, request.getProfileId());
        } finally {
            log.info("PUT api/v1/movie/{}/favourite/remove - DONE");
        }
    }

    @GetMapping("/favourites")
    public ResponseEntity<Set<Movie>> getFavourites(@PathParam(value = "id") Long profileId) {
        try {
            log.info("GET api/v1/movie/favourites - START");
            return ResponseEntity.ok(movieService.getFavourites(profileId));
        } finally {
            log.info("GET api/v1/movie/favourites - DONE");
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Set<Movie>> getMoviesByCategory(@PathVariable String categoryName) {
        try {
            log.info("GET api/v1/movie/category/{} - START");
            return ResponseEntity.ok(movieService.getMoviesByCategory(categoryName));
        } finally {
            log.info("GET api/v1/movie/category/{} - DONE");
        }

    }
}
