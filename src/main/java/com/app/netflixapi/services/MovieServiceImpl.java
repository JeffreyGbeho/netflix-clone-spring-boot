package com.app.netflixapi.services;

import com.app.netflixapi.config.AuthenticationUserProvider;
import com.app.netflixapi.entities.Category;
import com.app.netflixapi.entities.Movie;
import com.app.netflixapi.entities.Profile;
import com.app.netflixapi.exceptions.MovieNotFoundException;
import com.app.netflixapi.exceptions.ProfileAcessDeniedException;
import com.app.netflixapi.exceptions.ProfileNotFound;
import com.app.netflixapi.repositories.CategoryRepository;
import com.app.netflixapi.repositories.MovieRepository;
import com.app.netflixapi.repositories.ProfileRepository;
import com.app.netflixapi.services.interfaces.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final ResourceLoader resourceLoader;
    private final MovieRepository movieRepository;
    private final AuthenticationUserProvider authenticationUserProvider;
    private final CategoryRepository categoryRepository;
    private final ProfileRepository profileRepository;

    @Value("${directory.movies}")
    private static String FORMAT;

    @Override
    public StreamingResponseBody streamingMovie(Long movieId) throws IOException {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

        InputStream videoStream = this.getMovieFile(movie.getFilename());

        StreamingResponseBody responseBody = outputStream -> {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = videoStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            videoStream.close();
        };

        return responseBody;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    @Override
    public void addMovieToList(Long id, Long profileId) {
        Profile profile = this.getProfile(profileId);

        Movie movie = movieRepository.findById(id).orElseThrow();
        Set<Movie> fav = profile.getFavourites();
        fav.add(movie);
        profile.setFavourites(fav);

        profileRepository.save(profile);
    }

    @Override
    public void removeMovieToList(Long id, Long profileId) {
        Profile profile = this.getProfile(profileId);

        Movie movie = movieRepository.findById(id).orElseThrow();
        Set<Movie> fav = profile.getFavourites();
        fav.remove(movie);
        profile.setFavourites(fav);

        profileRepository.save(profile);
    }

    @Override
    public Set<Movie> getFavourites(Long profileId) {
        Profile profile = this.getProfile(profileId);

        return profile.getFavourites();
    }

    @Override
    public Set<Movie> getMoviesByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow();

        return category.getMovies();
    }

    private Profile getProfile(Long profileId) {
        String email = authenticationUserProvider.getAuthenticatedEmail();

        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFound("Profile not found"));

        if (!profile.getUser().getEmail().equals(email)) {
            throw new ProfileAcessDeniedException("Profile doesn't match the user");
        }

        return profile;
    }

    private FileInputStream getMovieFile(String title) throws IOException {
        Resource resource = resourceLoader.getResource(String.format(FORMAT, title));
        String videoFilePath = resource.getFile().getAbsolutePath();

        // Check if the movie exists
        File videoFile = new File(videoFilePath);
        if (!videoFile.exists()) {
            throw new MovieNotFoundException("The movie doesn't exist");
        }

        return new FileInputStream(videoFile);
    }
}
