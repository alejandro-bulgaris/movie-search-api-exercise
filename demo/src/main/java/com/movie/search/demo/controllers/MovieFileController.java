package com.movie.search.demo.controllers;

import com.movie.search.demo.model.Movie;
import com.movie.search.demo.services.MovieFileService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/movies/file")
public class MovieFileController {

    private final MovieFileService movieFileService;

    public MovieFileController(MovieFileService movieFileService) {
        this.movieFileService = movieFileService;
    }

    @GetMapping
    public List<Movie> getAllMovies() throws IOException {
        return movieFileService.getMovies();
    }

    @GetMapping("/search")
    public List<Movie> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String genre) throws IOException {
        return movieFileService.searchByTitle(title, year, genre);
    }
}
