package io.github.cgew85;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * Created by cgew85 on 04.07.2017.
 */
@AllArgsConstructor
public class MovieDTO implements Serializable {

    @Getter
    @Setter
    private ObjectId objectId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Movie.CASING casing;

    @Getter
    @Setter
    private Movie.CUT cut;
}
