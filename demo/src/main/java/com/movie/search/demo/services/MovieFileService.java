package com.movie.search.demo.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.search.demo.model.Movie;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieFileService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Movie> cachedMovies;

    private void loadMoviesIfNeeded() throws IOException {
        if (cachedMovies == null) {
            try (InputStream is = getClass().getResourceAsStream("/movies.json")) {
                cachedMovies = objectMapper.readValue(is, new TypeReference<List<Movie>>() {
                });
            }
        }
    }

    public List<Movie> getMovies() throws IOException {
        loadMoviesIfNeeded();
        return cachedMovies;
    }

    public List<Movie> searchByTitle(String title, Integer year, String genre) throws IOException {

        // Step 1: Load movies once
        loadMoviesIfNeeded();

        // Step 2: Prepare lowercase title search if provided
        String lowerTitle = title != null ? title.toLowerCase() : null;

        // Step 3: Prepare lowercase genre search if provided
        String lowerGenre = genre != null ? genre.toLowerCase() : null;

        // Step 4: Stream the cached movies
        return cachedMovies.stream()
                // Step 4a: Filter by title if provided
                .filter(movie -> {
                    if (lowerTitle == null) {
                        return true;
                    }
                    return movie.getTitle() != null && movie.getTitle().toLowerCase().contains(lowerTitle);
                })
                // Step 4b: Filter by year if provided
                .filter(movie -> year == null || movie.getYear() == year)
                // Step 4c: Filter by genre if provided
                .filter(movie -> {
                    if (lowerGenre == null) {
                        return true;
                    }

                    boolean matchesSingleGenre = movie.getGenre() != null
                            && movie.getGenre().toLowerCase().equals(lowerGenre);

                    boolean matchesGenreList = movie.getGenres() != null &&
                            movie.getGenres().stream().anyMatch(g -> g.toLowerCase().equals(lowerGenre));

                    return matchesSingleGenre || matchesGenreList;
                })
                // Step 5: Collect results
                .collect(Collectors.toList());
    }

}
