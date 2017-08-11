package io.github.cgew85;

import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * Created by cgew85 on 04.07.2017.
 */
@NoArgsConstructor
public class MovieMapper {

    private Logger logger = Logger.getLogger(MovieMapper.class);

    public Optional<MovieDTO> convertToDto(final Movie movie) {
        if (isNull(movie)) {
            logger.error("Movie cannot be null.");
            return Optional.empty();
        } else {
            return Optional.of(
                    new MovieDTO(movie.getObjectId(),
                            movie.getName(),
                            movie.getCasing(),
                            movie.getCut())
            );
        }
    }
}
