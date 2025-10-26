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

    /**
     * @implNote Spring Boot services and controllers are singletons and
     *           ObjectMapper is thread-safe therefore for a demo making it static
     *           seems not required but just noting as something that deserves more
     *           consideration for a production ready application and likewise the
     *           idea of caching the movie data in memory it's just an example to
     *           demonstrate a simple strategy to improve the api performace but
     *           also deserves more considerantion for a production ready
     *           application
     */
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Movie> cachedMovies;
    private static final int MAX_RESULTS = 25; // Limit max results returned for demoing purposes

    private void loadMoviesIfNeeded() throws IOException {
        if (cachedMovies == null) {
            try (InputStream is = getClass().getResourceAsStream("/movies.json")) {
                cachedMovies = objectMapper.readValue(is, new TypeReference<List<Movie>>() {
                });
            }
        }
    }

    public List<Movie> searchMovies(String title, Integer year, String genre) throws IOException {

        // Load movies once
        loadMoviesIfNeeded();

        // Prepare lowercase title search if provided
        String lowerTitle = title != null ? title.toLowerCase() : null;

        // Prepare lowercase genre search if provided
        String lowerGenre = genre != null ? genre.toLowerCase() : null;

        // Stream the cached movies
        return cachedMovies.stream()
                // Filter by title if provided
                .filter(movie -> {
                    if (lowerTitle == null) {
                        return true;
                    }
                    return movie.getTitle() != null && movie.getTitle().toLowerCase().contains(lowerTitle);
                })
                // Filter by year if provided
                .filter(movie -> year == null || movie.getYear() == year)
                // Filter by genre if provided
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
                .limit(MAX_RESULTS)
                // Collect results
                .collect(Collectors.toList());
    }

}
