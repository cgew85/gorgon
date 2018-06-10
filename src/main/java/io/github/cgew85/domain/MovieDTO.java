package io.github.cgew85.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.io.Serializable;

import static io.github.cgew85.domain.Movie.*;

/**
 * Created by cgew85 on 04.07.2017.
 */
@AllArgsConstructor
@Builder
@Data
public class MovieDTO implements Serializable {

    private ObjectId objectId;
    private String name;
    private CASING casing;
    private CUT cut;
    private FORMAT format;
}
