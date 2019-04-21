package io.github.cgew85.service;

import feign.Param;
import feign.RequestLine;
import io.github.cgew85.domain.OmdbItem;

/**
 * Created by cgew85 on 21.04.2019.
 */
public interface OmdbClient {

    @RequestLine("GET /?apikey={apikey}&t={title}&y={year}")
    OmdbItem get(@Param("apikey") String apiKey, @Param("title") String title, @Param("year") String year);
}
