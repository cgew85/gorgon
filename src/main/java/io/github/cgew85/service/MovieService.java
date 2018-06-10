package io.github.cgew85.service;

import io.github.cgew85.domain.Movie;
import io.github.cgew85.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Created by cgew85 on 10.06.2018.
 */
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie saveMovie(Movie movie) {
        requireNonNull(movie, "movie cannot be null");
        return movieRepository.save(movie);
    }

    public void deleteMovie(Movie movie) {
        requireNonNull(movie, "movie cannot be null");
        movieRepository.delete(movie);
    }
}
