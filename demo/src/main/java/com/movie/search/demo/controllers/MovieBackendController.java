package com.movie.search.demo.controllers;

import com.movie.search.demo.model.Movie;
import com.movie.search.demo.services.MovieFileService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * @implNote Controller without authentication to demonstrate angular single
 *           page app
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular app to search movies
@RequestMapping("/backend")
public class MovieBackendController {

    private final MovieFileService movieFileService;

    public MovieBackendController(MovieFileService movieFileService) {
        this.movieFileService = movieFileService;
    }

    @GetMapping("/search")
    public List<Movie> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String genre) throws IOException {
        return movieFileService.searchMovies(title, year, genre);
    }

}
