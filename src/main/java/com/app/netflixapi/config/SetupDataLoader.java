package com.app.netflixapi.config;

import com.app.netflixapi.entities.Category;
import com.app.netflixapi.entities.Movie;
import com.app.netflixapi.repositories.CategoryRepository;
import com.app.netflixapi.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;

    private static boolean alreadySetup = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        this.createCategories();
        this.createMovies();

        alreadySetup = true;
    }

    private void createCategories() {
        String[] categories = {"Action", "Anime", "Asian", "British", "Comedies", "Crime", "Docuseries", "Dramas", "European", "Food & Travel", "French", "Horror", "International", "International Women's Month", "Kids", "Mysteries", "Reality", "Romance", "Sci-Fi & Fantasy", "Science & Nature", "Stand-Up & Talk Shows", "Teen", "That French Feeling", "Thriller", "US"};
        for (String category : categories) {
            Category newCategory = new Category();
            newCategory.setName(category);
            categoryRepository.save(newCategory);
        }
    }

    @Transactional
    public void createMovies() {
        Movie movie = new Movie();
        movie.setAuthor("Blender Studio");
        movie.setDescription("Un film sublime sur le printemps");
        movie.setDuration(4000);
        movie.setLimitAge(12);
        movie.setTitle("Spring");
        movie.setFilename("Spring");
        movie.setThumbnailUrl("https://i.ytimg.com/vi/MXZ6y9JADw0/maxresdefault.jpg");
        Set<Category> categories = new HashSet<>();
        Category c = categoryRepository.findByName("Action").orElse(null);
        if (c != null) {
            categories.add(c);
        }
        movie.setCategories(categories);

        movieRepository.save(movie);

        movie = new Movie();
        movie.setAuthor("Blender Studio");
        movie.setDescription("on vole c'est chouette");
        movie.setDuration(5000);
        movie.setLimitAge(3);
        movie.setTitle("Wing it");
        movie.setFilename("WING_IT");
        movie.setThumbnailUrl("https://i.ytimg.com/vi/u9lj-c29dxI/maxresdefault.jpg");
        categories = new HashSet<>();
        c = categoryRepository.findByName("Action").orElse(null);
        if (c != null) {
            categories.add(c);
        }
        movie.setCategories(categories);

        movieRepository.save(movie);

        movie = new Movie();
        movie.setAuthor("Blender Studio");
        movie.setDescription("Spoon ? Tu veux dire une cuillère");
        movie.setDuration(4000);
        movie.setLimitAge(12);
        movie.setTitle("Spoon");
        movie.setFilename("Spoon");
        movie.setThumbnailUrl("https://i.vimeocdn.com/video/1441814213-6d73963d8a2e263f53bf2cc16d0c1e4cc218cf4e000b49cfaa929860e5de6cf9-d?f=webp");
        categories = new HashSet<>();
        c = categoryRepository.findByName("Action").orElse(null);
        if (c != null) {
            categories.add(c);
        }
        movie.setCategories(categories);

        movieRepository.save(movie);

        movie = new Movie();
        movie.setAuthor("Blender Studio");
        movie.setDescription("chargé les amis");
        movie.setDuration(4000);
        movie.setLimitAge(12);
        movie.setTitle("Charge");
        movie.setFilename("CHARGE");
        movie.setThumbnailUrl("https://ddz4ak4pa3d19.cloudfront.net/cache/3b/27/3b2766793754ad4b84335baa2653892e.jpg");
        categories = new HashSet<>();
        c = categoryRepository.findByName("Action").orElse(null);
        if (c != null) {
            categories.add(c);
        }
        movie.setCategories(categories);

        movieRepository.save(movie);

        movie = new Movie();
        movie.setAuthor("Blender Studio");
        movie.setDescription("L'agent qui est un barbershop");
        movie.setDuration(4000);
        movie.setLimitAge(12);
        movie.setTitle("Agent 327: Operation Barbershop");
        movie.setFilename("Agent_327_Operation_Barbershop");
        movie.setThumbnailUrl("https://ddz4ak4pa3d19.cloudfront.net/cache/3d/b2/3db2889cfd85ebd2168ce9f8815e4112.jpg");
        categories = new HashSet<>();
        c = categoryRepository.findByName("Action").orElse(null);
        if (c != null) {
            categories.add(c);
        }
        movie.setCategories(categories);

        movieRepository.save(movie);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
