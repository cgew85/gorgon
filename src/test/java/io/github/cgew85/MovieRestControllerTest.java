package io.github.cgew85;

import io.github.cgew85.controller.MovieRestController;
import io.github.cgew85.mapper.MovieMapper;
import io.github.cgew85.service.MovieService;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static java.util.Objects.nonNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by cgew85 on 10.06.2018.
 */
public class MovieRestControllerTest {

    private MovieRestController movieRestController;

    @Before
    public void setUp() {
        MovieMapper movieMapper = mock(MovieMapper.class);
        MovieService movieService = mock(MovieService.class);
        when(movieService.getAllMovies()).thenReturn(Collections.emptyList());
        movieRestController = new MovieRestController(movieService, movieMapper);
    }

    @Test
    public void testMovieRestController() {
        assertThat(nonNull(movieRestController), is(true));
        assertThat(movieRestController.getAllMovies().size(), is(0));
    }
}