package io.github.cgew85.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by cgew85 on 21.04.2019.
 */
@Service
public class OmdbService {

    @Value("${omdb.api.key}")
    @Getter
    private String omdbApiKey;


}
