package io.github.cgew85.mapper;

import io.github.cgew85.domain.Movie;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by cgew85 on 04.07.2017.
 */
@NoArgsConstructor
@Slf4j
@Component
public class MovieMapper {

    private Map<String, Movie.CUT> mapCut = new HashMap<>();
    private Map<String, Movie.CASING> mapCasing = new HashMap<>();
    private Map<String, Movie.FORMAT> mapFormat = new HashMap<>();

    public Set<String> getMapCutUiTexts() {
        if (mapCut.isEmpty()) initMapCut();
        return mapCut.keySet();
    }

    public Set<String> getMapCasingUiTexts() {
        if (mapCasing.isEmpty()) initMapCasing();
        return mapCasing.keySet();
    }

    public Set<String> getMapFormatUiTexts() {
        if (mapFormat.isEmpty()) initMapFormat();
        return mapFormat.keySet();
    }

    public Object mapCutCasingOrFormat(String input, Class clazz) {
        if (clazz.toString().equals(Movie.CUT.class.toString())) {
            if (mapCut.isEmpty()) initMapCut();
            return mapCut.get(input);
        }
        if (clazz.toString().equals(Movie.CASING.class.toString())) {
            if (mapCasing.isEmpty()) initMapCasing();
            return mapCasing.get(input);
        }
        if (clazz.toString().equals(Movie.FORMAT.class.toString())) {
            if (mapFormat.isEmpty()) initMapFormat();
            return mapFormat.get(input);
        }

        return null;
    }

    public String getUiTextCut(Movie.CUT cut) {
        if (mapCut.isEmpty()) initMapCut();
        AtomicReference<String> returnValue = new AtomicReference<>();
        mapCut.forEach((key, value) -> {
            if (value.equals(cut)) returnValue.set(key);
        });

        return returnValue.get();
    }

    public String getUiTextCasing(Movie.CASING casing) {
        if (mapCasing.isEmpty()) initMapCasing();
        AtomicReference<String> returnValue = new AtomicReference<>();
        mapCasing.forEach((key, value) -> {
            if (value.equals(casing)) returnValue.set(key);
        });

        return returnValue.get();
    }

    public String getUiTextFormat(Movie.FORMAT format) {
        if (mapFormat.isEmpty()) initMapFormat();
        AtomicReference<String> returnValue = new AtomicReference<>();
        mapFormat.forEach((key, value) -> {
            if (value.equals(format)) returnValue.set(key);
        });

        return returnValue.get();
    }

    private void initMapCut() {
        mapCut.put("Director's Cut", Movie.CUT.DIRECTORS_CUT);
        mapCut.put("Theatrical Cut", Movie.CUT.THEATRICAL_CUT);
        mapCut.put("Extended Cut", Movie.CUT.EXTENDED_CUT);
        mapCut.put("Uncut", Movie.CUT.UNCUT);
    }

    private void initMapCasing() {
        mapCasing.put("Amaray", Movie.CASING.AMARAY);
        mapCasing.put("Amaray slim", Movie.CASING.AMARAY_SLIM);
        mapCasing.put("Steelbook", Movie.CASING.STEELBOOK);
    }

    private void initMapFormat() {
        mapFormat.put("DVD", Movie.FORMAT.DVD);
        mapFormat.put("BluRay", Movie.FORMAT.BLURAY);
        mapFormat.put("VHS", Movie.FORMAT.VHS);
    }
}
