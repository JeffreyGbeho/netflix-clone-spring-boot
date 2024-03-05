package com.app.netflixapi.controllers;

import com.app.netflixapi.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {
   private final MovieService movieService;

    @GetMapping(value = "/{title}", produces = "video/mp4")
    public Mono<Resource> getMovie(@PathVariable String title) {
        return movieService.getMovie(title);
    }

}
