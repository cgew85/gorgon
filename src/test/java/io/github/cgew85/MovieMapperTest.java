package io.github.cgew85;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by cgew85 on 20.04.2018.
 */
public class MovieMapperTest {

    private MovieMapper movieMapper;

    @Before
    public void setUp() {
        movieMapper = new MovieMapper();
    }

    @Test
    public void testWithNullInput() {
        assertEquals(Optional.empty(), movieMapper.convertToDto(null));
    }

    @Test
    public void testMappingWithValidInput() {
        Movie movie = new Movie();
        movie.setObjectId(null);
        movie.setName("name");
        movie.setCasing(Movie.CASING.AMARAY);
        movie.setCut(Movie.CUT.THEATRICAL_CUT);
        movie.setFormat(Movie.FORMAT.BLURAY);
        Optional<MovieDTO> optionalMovieDTO = movieMapper.convertToDto(movie);
        assertTrue(optionalMovieDTO.isPresent());
        MovieDTO movieDTO = optionalMovieDTO.get();
        assertThat(movieDTO.getName(), is(movie.getName()));
        assertThat(movieDTO.getCasing(), is(movie.getCasing()));
        assertThat(movieDTO.getCut(), is(movie.getCut()));
        assertThat(movieDTO.getFormat(), is(movie.getFormat()));
    }
}