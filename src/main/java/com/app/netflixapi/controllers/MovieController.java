package com.app.netflixapi.controllers;

import com.app.netflixapi.dtos.FavouriteRequest;
import com.app.netflixapi.entities.Movie;
import com.app.netflixapi.services.MovieService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Mono;

import java.io.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {
   private final MovieService movieService;

    @GetMapping("/streaming/{movieId}")
    public ResponseEntity<StreamingResponseBody> getMovie(@PathVariable Long movieId) throws IOException {
        StreamingResponseBody responseBody = movieService.streamingMovie(movieId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
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
    public void addMovieToList(@PathVariable Long id, @RequestBody FavouriteRequest request) {
        movieService.addMovieToList(id, request.getProfileId());
    }

    @PutMapping("/{id}/favourite/remove")
    public void removeMovieToList(@PathVariable Long id, @RequestBody FavouriteRequest request) {
        movieService.removeMovieToList(id, request.getProfileId());
    }

    @GetMapping("/favourites")
    public ResponseEntity<Set<Movie>> getFavourites(@PathParam(value = "id") Long profileId) {
        return ResponseEntity.ok(movieService.getFavourites(profileId));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Set<Movie>> getMoviesByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(movieService.getMoviesByCategory(categoryName));
    }
}
