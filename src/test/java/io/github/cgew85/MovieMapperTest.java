package io.github.cgew85;

import io.github.cgew85.mapper.MovieMapper;
import org.junit.Before;

/**
 * Created by cgew85 on 20.04.2018.
 */
public class MovieMapperTest {

    private MovieMapper movieMapper;

    @Before
    public void setUp() {
        movieMapper = new MovieMapper();
    }
}