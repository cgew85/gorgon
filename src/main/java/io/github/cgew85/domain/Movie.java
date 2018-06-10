package io.github.cgew85.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
        AMARAY_SLIM,
        STEELBOOK
    }

    public enum CUT {
        THEATRICAL_CUT,
        DIRECTORS_CUT,
        EXTENDED_CUT,
        UNCUT
    }

    @Id
    private ObjectId objectId;

    @Indexed
    private String name;

    @Field("CASING")
    private CASING casing;

    @Transient
    private String casingUi;

    @Field("CUT")
    private CUT cut;

    @Transient
    private String cutUi;

    @Field("FORMAT")
    private FORMAT format;

    @Transient
    private String formatUi;
}
