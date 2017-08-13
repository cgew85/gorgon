package io.github.cgew85;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "movies")
public class Movie {

    public enum FORMAT {
        DVD,
        BLURAY,
        VHS
    }

    public enum CASING {
        AMARAY,
        STEELBOOK
    }

    public enum CUT {
        THEATRICAL_CUT,
        DIRECTORS_CUT
    }

    @Id
    private ObjectId objectId;

    @Indexed
    private String name;

    @Field("CASING")
    private CASING casing;

    @Field("CUT")
    private CUT cut;

    @Field("FORMAT")
    private FORMAT format;

}
