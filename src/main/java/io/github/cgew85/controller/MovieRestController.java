package io.github.cgew85.controller;

import io.github.cgew85.domain.MovieDTO;
import io.github.cgew85.mapper.MovieMapper;
import io.github.cgew85.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by cgew85 on 04.07.2017.
 */
@RestController
public class MovieRestController {

    private MovieService movieService;
    private MovieMapper movieMapper;

    @Autowired
    public MovieRestController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @GetMapping(value = "/movies", produces = APPLICATION_JSON_VALUE)
    public List<MovieDTO> getAllMovies() {
        List<MovieDTO> movieDTOList = new ArrayList<>();
        movieService.getAllMovies().forEach(movie ->
                movieMapper.convertToDto(movie).ifPresent(movieDTOList::add));

        return movieDTOList;
    }
}
