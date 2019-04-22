package io.github.cgew85.service;

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import io.github.cgew85.domain.Movie;
import io.github.cgew85.domain.OmdbItem;
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

    private final OmdbService omdbService;
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(OmdbService omdbService, MovieRepository movieRepository) {
        this.omdbService = omdbService;
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

    public OmdbItem getOmdbItemByNameAndYear(String name, String year) {
        return prepareOmdbClient().get(omdbService.getOmdbApiKey(), name, year);
    }

    private OmdbClient prepareOmdbClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(OmdbClient.class))
                .logLevel(Logger.Level.FULL)
                .target(OmdbClient.class, "http://www.omdbapi.com");
    }
}
