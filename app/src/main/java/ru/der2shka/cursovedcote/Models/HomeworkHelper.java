package ru.der2shka.cursovedcote.Models;

import android.graphics.Path;

import java.util.Optional;

import ru.der2shka.cursovedcote.db.entity.Homework;
import ru.der2shka.cursovedcote.db.entity.Note;

public class HomeworkHelper {
    private static Optional<HomeworkHelper> instance = Optional.ofNullable( null );

    private Optional<Homework> homework = Optional.ofNullable( null );

    private HomeworkHelper() {}

    /**
     * Return instance of HomeworkHelper and create them if it not exist.
     * @return HomeworkHelper instance.
     * **/
    public static HomeworkHelper getInstance() {
        if ( !instance.isPresent() ) {
            instance = Optional.ofNullable( new HomeworkHelper() );
        }

        return instance.get();
    }

    /**
     * Set homework optional value.
     * @param nhomework new homework value.
     * @return true if value was updated else false.
     * **/
    public Boolean setHomeworkValue(Optional<Homework> nhomework) {
        if (nhomework.isPresent()) {
            homework = nhomework;

            return true;
        }

        return false;
    }

    /**
     * Return homework object.
     * @return homework value if persist, else Homework(-1, "\\_( -_ -)_/", "", 0L, 0L, 0L, 0, 0)
     * **/
    public Homework getHomeworkValue() {
        return homework.orElse(
                new Homework(
                        -1,
                        "\\_( -_ -)_/",
                        "",
                        0L,
                        0L,
                        0L,
                        0,
                        0
                )
        );
    }
}
