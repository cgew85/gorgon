package io.github.cgew85;

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
        MovieRepository movieRepository = mock(MovieRepository.class);
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());
        movieRestController = new MovieRestController(movieRepository);
    }

    @Test
    public void testMovieRestController() {
        assertThat(nonNull(movieRestController), is(true));
        assertThat(movieRestController.getAllMovies().size(), is(0));
    }
}