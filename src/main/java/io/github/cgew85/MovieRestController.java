package io.github.cgew85;

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

    private MovieRepository movieRepository;

    @Autowired
    public MovieRestController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping(value = "/movies", produces = APPLICATION_JSON_VALUE)
    public List<MovieDTO> getAllMovies() {
        List<MovieDTO> movieDTOList = new ArrayList<>();
        MovieMapper movieMapper = new MovieMapper();
        movieRepository.findAll().parallelStream().forEach(movie ->
                movieMapper.convertToDto(movie).ifPresent(movieDTOList::add));

        return movieDTOList;
    }
}
