package com.movie.search.demo.controllers;

import com.movie.search.demo.model.Movie;
import com.movie.search.demo.services.MovieFileService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * @implNote Controller with basic authentication although I wasn't sure if
 *           basic authentication was used loosely or not. In example if a
 *           bearer token would have been acceptable as well for the demo
 */
@RestController
@RequestMapping("/api")
public class MovieFileController {

    private final MovieFileService movieFileService;

    public MovieFileController(MovieFileService movieFileService) {
        this.movieFileService = movieFileService;
    }

    @GetMapping("/movies/file/search")
    public List<Movie> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String genre) throws IOException {
        return movieFileService.searchMovies(title, year, genre);
    }
}
