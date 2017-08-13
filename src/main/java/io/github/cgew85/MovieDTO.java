package io.github.cgew85;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.io.Serializable;

import static io.github.cgew85.Movie.*;

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
    private CASING casing;

    @Getter
    @Setter
    private CUT cut;

    @Getter
    @Setter
    private FORMAT format;
}
