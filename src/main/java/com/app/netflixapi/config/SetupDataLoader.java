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
        movie.setDescription("Film prenant dans lequel on s'ennuie pas une seconde");
        movie.setDuration(4000);
        movie.setLimitAge(12);
        movie.setTitle("String");
        Set<Category> categories = new HashSet<>();
        Category c = categoryRepository.findByName("Action").orElse(null);
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
