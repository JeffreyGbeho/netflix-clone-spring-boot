package com.app.netflixapi.services.interfaces;

import com.app.netflixapi.entities.Movie;
import com.app.netflixapi.entities.Profile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface MovieService {
    StreamingResponseBody streamingMovie(Long movieId) throws IOException;
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    void addMovieToList(Long id, Long profileId);
    void removeMovieToList(Long id, Long profileId);
    Set<Movie> getFavourites(Long profileId);
    Set<Movie> getMoviesByCategory(String categoryName);
}
