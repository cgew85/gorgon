package io.github.cgew85;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * Created by cgew85 on 04.07.2017.
 */
@NoArgsConstructor
@Slf4j
public class MovieMapper {

    public Optional<MovieDTO> convertToDto(final Movie movie) {
        if (isNull(movie)) {
            log.error("Movie cannot be null.");
            return Optional.empty();
        } else {
            return Optional.of(
                    new MovieDTO(
                            movie.getObjectId(),
                            movie.getName(),
                            movie.getCasing(),
                            movie.getCut(),
                            movie.getFormat())
            );
        }
    }
}
