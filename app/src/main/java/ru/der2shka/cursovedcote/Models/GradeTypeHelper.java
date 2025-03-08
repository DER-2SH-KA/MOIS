package ru.der2shka.cursovedcote.Models;

import java.util.Optional;

import ru.der2shka.cursovedcote.db.entity.GradeType;
import ru.der2shka.cursovedcote.db.entity.Note;

public class GradeTypeHelper {
    private static Optional<GradeTypeHelper> instance = Optional.ofNullable( null );

    private Optional<GradeType> gradeType = Optional.ofNullable( null );

    private GradeTypeHelper() {}

    /**
     * Return instance of GradeTypeHelper and create them if it not exist.
     * @return GradeTypeHelper instance.
     * **/
    public static GradeTypeHelper getInstance() {
        if ( !instance.isPresent() ) {
            instance = Optional.ofNullable( new GradeTypeHelper() );
        }

        return instance.get();
    }

    /**
     * Set grade type optional value.
     * @param ngradeType new grade type value.
     * @return true if value was updated else false.
     * **/
    public Boolean setGradeTypeValue(Optional<GradeType> ngradeType) {
        if (ngradeType.isPresent()) {
            gradeType = ngradeType;

            return true;
        }

        return false;
    }

    /**
     * Return grade type object.
     * @return grade type value if persist, else GradeType(0, "None", 1, 1)
     * **/
    public GradeType getGradeTypeValue() {
        return gradeType.orElse(
                new GradeType(
                        0,
                        "None",
                        1,
                        1
                )
        );
    }
}
