package ru.der2shka.cursovedcote.Models;

import java.util.Optional;

import ru.der2shka.cursovedcote.db.entity.Grade;
import ru.der2shka.cursovedcote.db.entity.GradeType;
import ru.der2shka.cursovedcote.db.entity.Note;
import ru.der2shka.cursovedcote.db.entity.StudySubject;

public class GradeHelper {
    private static Optional<GradeHelper> instance = Optional.ofNullable( null );

    private Optional<Grade> grade = Optional.ofNullable( null );

    private GradeHelper() {}

    /**
     * Return instance of GradeHelper and create them if it not exist.
     * @return GradeHelper instance.
     * **/
    public static GradeHelper getInstance() {
        if ( !instance.isPresent() ) {
            instance = Optional.ofNullable( new GradeHelper() );
        }

        return instance.get();
    }

    /**
     * Set grade optional value.
     * @param nGrade new grade value.
     * @return true if value was updated else false.
     * **/
    public Boolean setGradeValue(Optional<Grade> nGrade) {
        if (nGrade.isPresent()) {
            grade = nGrade;

            return true;
        }

        return false;
    }

    /**
     * Return grade object.
     * @return grade value if persist, else Grade(1, 1, 0L, 0, 0)
     * **/
    public Grade getGradeValue() {
        return grade.orElse(
                new Grade(
                        -1,
                        1,
                        0L,
                        0,
                        0
                )
        );
    }
}
