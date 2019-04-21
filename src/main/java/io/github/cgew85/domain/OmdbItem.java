package io.github.cgew85.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by cgew85 on 21.04.2019.
 */
@Data
@NoArgsConstructor
public class OmdbItem {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Rated")
    private String rated;

    @JsonProperty("Released")
    private String released;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Writer")
    private String writer;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Awards")
    private String awards;

    @JsonProperty("Poster")
    private String poster;

    //    private String ratings;

    @JsonProperty("Metascore")
    private String metascore;

    private String imdbRating;
    private String imdbVotes;
    private String imdbID;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Production")
    private String production;

    @JsonProperty("BoxOffice")
    private String boxOffice;
}
