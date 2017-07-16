package io.github.cgew85;

import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by cgew85 on 04.07.2017.
 */
@NoArgsConstructor
public class MovieMapper {

    Logger logger = Logger.getLogger(MovieMapper.class);

    public Optional<MovieDTO> convertToDto(final Movie movie) {
        if (Objects.isNull(movie)) {
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
